package reporting;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

/**
 * Reporte HTML tipo "funnel" que clasifica la ejecucion de la suite en:
 *  - Aprobadas        : tests que pasaron.
 *  - Reprobadas       : tests que fallaron (con el mensaje de error).
 *  - No implementadas : tests omitidos via SkipException (stubs pendientes).
 *
 * Se registra automaticamente mediante el mecanismo ServiceLoader de TestNG,
 * a traves del archivo:
 *   src/test/resources/META-INF/services/org.testng.ITestNGListener
 *
 * Al finalizar la suite genera el archivo "reporte-funnel.html" dentro del
 * directorio de salida de TestNG (normalmente "test-output").
 */
public class FunnelHtmlReporter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        List<ITestResult> passed = new ArrayList<>();
        List<ITestResult> failed = new ArrayList<>();
        List<ITestResult> skipped = new ArrayList<>();

        for (ISuite suite : suites) {
            for (ISuiteResult suiteResult : suite.getResults().values()) {
                ITestContext context = suiteResult.getTestContext();
                passed.addAll(context.getPassedTests().getAllResults());
                failed.addAll(context.getFailedTests().getAllResults());
                skipped.addAll(context.getSkippedTests().getAllResults());
            }
        }

        Comparator<ITestResult> byName = Comparator
                .comparing((ITestResult r) -> r.getTestClass().getName())
                .thenComparing(r -> r.getMethod().getMethodName());
        passed.sort(byName);
        failed.sort(byName);
        skipped.sort(byName);

        String html = buildHtml(passed, failed, skipped);

        Path output = Paths.get(outputDirectory, "reporte-funnel.html");
        try {
            if (output.getParent() != null) {
                Files.createDirectories(output.getParent());
            }
            try (Writer writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
                writer.write(html);
            }
            System.out.println("==================================================");
            System.out.println(" Reporte funnel generado:");
            System.out.println(" " + output.toAbsolutePath());
            System.out.println("==================================================");
        } catch (IOException e) {
            System.err.println("No se pudo generar el reporte funnel: " + e.getMessage());
        }
    }

    private String buildHtml(List<ITestResult> passed, List<ITestResult> failed, List<ITestResult> skipped) {
        int nPassed = passed.size();
        int nFailed = failed.size();
        int nSkipped = skipped.size();
        int total = nPassed + nFailed + nSkipped;

        double pctPassed = total == 0 ? 0 : (nPassed * 100.0 / total);
        double pctFailed = total == 0 ? 0 : (nFailed * 100.0 / total);
        double pctSkipped = total == 0 ? 0 : (nSkipped * 100.0 / total);

        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n");
        sb.append("<meta charset=\"UTF-8\">\n");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        sb.append("<title>Reporte Funnel - Pruebas Automatizadas</title>\n");
        sb.append("<style>\n").append(css()).append("</style>\n");
        sb.append("</head>\n<body>\n");

        sb.append("<header>\n");
        sb.append("  <h1>Reporte de Ejecucion - Funnel de Pruebas</h1>\n");
        sb.append("  <p class=\"sub\">transform-cv-QA &middot; Selenium + TestNG &middot; Generado el ")
          .append(timestamp).append("</p>\n");
        sb.append("</header>\n");

        sb.append("<section class=\"cards\">\n");
        sb.append(card("Total", total, "card-total"));
        sb.append(card("Aprobadas", nPassed, "card-pass"));
        sb.append(card("Reprobadas", nFailed, "card-fail"));
        sb.append(card("No implementadas", nSkipped, "card-skip"));
        sb.append("</section>\n");

        sb.append("<section class=\"funnel\">\n");
        sb.append("  <h2>Distribucion de resultados</h2>\n");
        sb.append("  <div class=\"bar\">\n");
        if (nPassed > 0) {
            sb.append("    <div class=\"seg seg-pass\" style=\"width:").append(fmt(pctPassed))
              .append("%\">").append(fmt(pctPassed)).append("%</div>\n");
        }
        if (nFailed > 0) {
            sb.append("    <div class=\"seg seg-fail\" style=\"width:").append(fmt(pctFailed))
              .append("%\">").append(fmt(pctFailed)).append("%</div>\n");
        }
        if (nSkipped > 0) {
            sb.append("    <div class=\"seg seg-skip\" style=\"width:").append(fmt(pctSkipped))
              .append("%\">").append(fmt(pctSkipped)).append("%</div>\n");
        }
        if (total == 0) {
            sb.append("    <div class=\"seg\" style=\"width:100%;background:#9aa5b1\">Sin resultados</div>\n");
        }
        sb.append("  </div>\n");
        sb.append("  <div class=\"legend\">\n");
        sb.append("    <span><i class=\"dot dot-pass\"></i> Aprobadas (").append(nPassed).append(")</span>\n");
        sb.append("    <span><i class=\"dot dot-fail\"></i> Reprobadas (").append(nFailed).append(")</span>\n");
        sb.append("    <span><i class=\"dot dot-skip\"></i> No implementadas (").append(nSkipped).append(")</span>\n");
        sb.append("  </div>\n");
        sb.append("</section>\n");

        sb.append(table("Reprobadas", "fail", failed, true));
        sb.append(table("No implementadas (pendientes)", "skip", skipped, true));
        sb.append(table("Aprobadas", "pass", passed, false));

        sb.append("<footer>Reporte generado automaticamente por FunnelHtmlReporter "
                + "(IReporter de TestNG).</footer>\n");
        sb.append("</body>\n</html>");
        return sb.toString();
    }

    private String card(String label, int value, String cssClass) {
        return "  <div class=\"card " + cssClass + "\">"
                + "<div class=\"cnum\">" + value + "</div>"
                + "<div class=\"clbl\">" + esc(label) + "</div></div>\n";
    }

    private String table(String title, String kind, List<ITestResult> results, boolean showReason) {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"results\">\n");
        sb.append("  <h2>").append(esc(title)).append(" <span class=\"count count-").append(kind)
          .append("\">").append(results.size()).append("</span></h2>\n");

        if (results.isEmpty()) {
            sb.append("  <p class=\"empty\">Sin registros.</p>\n</section>\n");
            return sb.toString();
        }

        sb.append("  <table>\n    <thead><tr>");
        sb.append("<th>Clase</th><th>Metodo</th><th>Caso / Matriz de Pruebas</th><th>Duracion</th>");
        if (showReason) {
            sb.append("<th>Detalle</th>");
        }
        sb.append("</tr></thead>\n    <tbody>\n");

        for (ITestResult r : results) {
            String cls = simpleClass(r.getTestClass().getName());
            String method = r.getMethod().getMethodName();
            String desc = r.getMethod().getDescription();
            if (desc == null || desc.isBlank()) {
                desc = "(sin descripcion)";
            }
            long ms = r.getEndMillis() - r.getStartMillis();

            sb.append("      <tr>");
            sb.append("<td>").append(esc(cls)).append("</td>");
            sb.append("<td class=\"mono\">").append(esc(method)).append("</td>");
            sb.append("<td>").append(esc(desc)).append("</td>");
            sb.append("<td class=\"dur\">").append(ms).append(" ms</td>");
            if (showReason) {
                String reason = "";
                Throwable t = r.getThrowable();
                if (t != null) {
                    reason = t.getMessage() != null ? t.getMessage() : t.getClass().getSimpleName();
                }
                sb.append("<td class=\"reason\">").append(esc(reason)).append("</td>");
            }
            sb.append("</tr>\n");
        }

        sb.append("    </tbody>\n  </table>\n</section>\n");
        return sb.toString();
    }

    private String simpleClass(String fqn) {
        if (fqn == null) {
            return "";
        }
        int i = fqn.lastIndexOf('.');
        return i >= 0 ? fqn.substring(i + 1) : fqn;
    }

    private String fmt(double d) {
        return String.format(Locale.US, "%.1f", d);
    }

    private String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private String css() {
        return ""
            + "* { box-sizing: border-box; margin: 0; padding: 0; }\n"
            + "body { font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;"
            + " background:#f4f6f8; color:#1f2933; padding:32px; }\n"
            + "header { margin-bottom:24px; }\n"
            + "header h1 { font-size:24px; color:#102a43; }\n"
            + "header .sub { color:#627d98; font-size:13px; margin-top:4px; }\n"
            + ".cards { display:flex; gap:16px; flex-wrap:wrap; margin-bottom:28px; }\n"
            + ".card { flex:1; min-width:160px; background:#fff; border-radius:10px;"
            + " padding:20px; box-shadow:0 1px 3px rgba(0,0,0,.08); border-left:5px solid #9aa5b1; }\n"
            + ".card .cnum { font-size:34px; font-weight:700; }\n"
            + ".card .clbl { font-size:12px; color:#627d98; margin-top:4px;"
            + " text-transform:uppercase; letter-spacing:.5px; }\n"
            + ".card-total { border-left-color:#334e68; }\n"
            + ".card-pass { border-left-color:#2f9e44; }\n"
            + ".card-pass .cnum { color:#2f9e44; }\n"
            + ".card-fail { border-left-color:#e03131; }\n"
            + ".card-fail .cnum { color:#e03131; }\n"
            + ".card-skip { border-left-color:#f08c00; }\n"
            + ".card-skip .cnum { color:#f08c00; }\n"
            + "section { margin-bottom:28px; }\n"
            + ".funnel h2, .results h2 { font-size:16px; color:#102a43; margin-bottom:12px; }\n"
            + ".bar { display:flex; height:38px; border-radius:8px; overflow:hidden;"
            + " background:#e4e7eb; }\n"
            + ".seg { display:flex; align-items:center; justify-content:center; color:#fff;"
            + " font-size:12px; font-weight:600; overflow:hidden; white-space:nowrap; }\n"
            + ".seg-pass { background:#2f9e44; }\n"
            + ".seg-fail { background:#e03131; }\n"
            + ".seg-skip { background:#f08c00; }\n"
            + ".legend { display:flex; gap:20px; margin-top:10px; font-size:13px; color:#627d98; }\n"
            + ".dot { display:inline-block; width:11px; height:11px; border-radius:50%;"
            + " margin-right:5px; }\n"
            + ".dot-pass { background:#2f9e44; }\n"
            + ".dot-fail { background:#e03131; }\n"
            + ".dot-skip { background:#f08c00; }\n"
            + "table { width:100%; border-collapse:collapse; background:#fff;"
            + " border-radius:10px; overflow:hidden; box-shadow:0 1px 3px rgba(0,0,0,.08); }\n"
            + "th, td { text-align:left; padding:10px 14px; font-size:13px;"
            + " border-bottom:1px solid #e4e7eb; vertical-align:top; }\n"
            + "th { background:#f0f4f8; color:#486581; text-transform:uppercase;"
            + " font-size:11px; letter-spacing:.5px; }\n"
            + "tr:last-child td { border-bottom:none; }\n"
            + ".mono { font-family:'Consolas','Courier New',monospace; font-size:12px; }\n"
            + ".dur { text-align:right; white-space:nowrap; color:#627d98; }\n"
            + ".reason { color:#e03131; font-size:12px; }\n"
            + ".count { display:inline-block; min-width:24px; padding:2px 9px;"
            + " border-radius:12px; font-size:12px; color:#fff; }\n"
            + ".count-pass { background:#2f9e44; }\n"
            + ".count-fail { background:#e03131; }\n"
            + ".count-skip { background:#f08c00; }\n"
            + ".empty { color:#9aa5b1; font-size:13px; font-style:italic; }\n"
            + "footer { text-align:center; color:#9aa5b1; font-size:12px; margin-top:32px; }\n";
    }
}
