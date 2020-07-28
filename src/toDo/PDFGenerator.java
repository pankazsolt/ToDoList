package toDo;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

public class PDFGenerator {
    public void PDFGenerator (String fnev, ObservableList<Data>info) {
    Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fnev + ".pdf"));
            document.open();
            
             float[] columnWidths = {2, 3, 3, 5};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("Task List"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("No.");
            table.addCell("Date");
            table.addCell("Owner");
            table.addCell("Task");
            table.setHeaderRows(1);
            
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            for (int i = 1; i <= info.size(); i++) {
                Data actualdata = info.get(i - 1);
                
                table.addCell(""+i);
                table.addCell(actualdata.getDate().toString());
                table.addCell(actualdata.getOwner());
                table.addCell(actualdata.getTask());
            }
            
            document.add(table);
            
            Chunk signature = new Chunk("\n\n To do list.");
            Paragraph base = new Paragraph(signature);
            document.add(base);
        } catch (Exception e) {
        }
    document.close();
            }
}

