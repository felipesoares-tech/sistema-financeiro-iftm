
import br.edu.iftm.jsf.entity.Produto;
import br.edu.iftm.jsf.logic.ProdutoLogic;
import br.edu.iftm.jsf.util.MimeTypes;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tika.Tika;

@WebServlet(name = "ImagemServlet", urlPatterns = {"/imagem"})
public class ImagemServlet extends HttpServlet {

    @Inject
    private ProdutoLogic logic;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.valueOf(request.getParameter("id"));
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Produto produto = logic.buscarPorId(id);
        if (produto == null || produto.getFotoBanco() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] imagemBytes = produto.getFotoBanco();
        Tika tika = new Tika();
        String detectedMimeType = tika.detect(produto.getFotoBanco());
        String mimeType = MimeTypes.getMimeType(detectedMimeType);
        response.setContentType(mimeType);
        response.setContentLength(imagemBytes.length);

        try (OutputStream out = response.getOutputStream()) {
            out.write(imagemBytes);
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
