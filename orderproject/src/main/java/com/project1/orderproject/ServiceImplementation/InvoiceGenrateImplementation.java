package com.project1.orderproject.ServiceImplementation;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.project1.orderproject.ExceptionHandler.Exception.Invoice_Not_Found;
import com.project1.orderproject.ExceptionHandler.Exception.OrderId_NotFOund;
import com.project1.orderproject.POJOs.Invoice;
import com.project1.orderproject.POJOs.Orders;
import com.project1.orderproject.POJOs.Product;
import com.project1.orderproject.Repositry.InventoryRepositry;
import com.project1.orderproject.Repositry.InvoiceRepositry;
import com.project1.orderproject.Repositry.OrderRepositry;
import com.project1.orderproject.Service.InvoicePdfGenrate;

@Service
public class InvoiceGenrateImplementation implements InvoicePdfGenrate{

    private  InvoiceRepositry  invoiceRepositry;
    public InvoiceGenrateImplementation(InvoiceRepositry invoiceRepositry, OrderRepositry orderRepositry,
                        InventoryRepositry inventoryRepositry
        ) {
        this.invoiceRepositry = invoiceRepositry;
        this.orderRepositry = orderRepositry;
        this.inventoryRepositry=inventoryRepositry;
    }

    private  OrderRepositry orderRepositry;
    private  InventoryRepositry inventoryRepositry;
    

    @Override
    public byte[] generat_invoice_pdf(String  order_id) {
        // TODO Auto-generated method stub
        Orders orders  = get_Order(order_id);
        Invoice  invoice   =  orders.getOrder_invoice();
        ByteArrayOutputStream outputStream =  new ByteArrayOutputStream();
        try {
            Document document  =  new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            document.add(new Paragraph("The  Invoice Genrated Pdf "));
            document.add(new Paragraph("Date : "+invoice.getInvoice_date()));
            document.add(new Paragraph("Invoice Id : "+invoice.getInvoiceId()));
            document.add(new Paragraph("User ID  : "+invoice.getUser().getId()));
            document.add(new Paragraph("User Name  : "+invoice.getUser().getUsername()));
            document.add(new Paragraph("Order Id : "+order_id));

            document.add(new Paragraph(""));
            document.addAuthor("Order Api ");
            document.addHeader("Company ", "Order  Api ");
            document.addTitle("Invoice");
            

            PdfPTable pTable =  new PdfPTable(5);
            pTable.addCell("Product ID");
            pTable.addCell("Product Category");
            pTable.addCell("Product Quantity");
            pTable.addCell("Product Price");
            pTable.addCell("Amount");

            for(String  product :  orders.getProductList().keySet()) {
                Optional<Product> product2 = inventoryRepositry.findById(product);
                pTable.addCell(product2.get().getId());
                pTable.addCell(""+product2.get().getCategory());
                pTable.addCell(""+orders.getProductList().get(product));
                pTable.addCell(""+product2.get().getPrice());
                pTable.addCell(""+(Double)(product2.get().getPrice()*orders.getProductList().get(product)));

            }
            document.add(pTable);
            document.add(new  Paragraph("The  Total  Amount  Paid : "+orders.getAmount()));

            document.close();

        }
        catch(Exception e) {
            throw  new  OrderId_NotFOund("ERROR  IN  GENRATING  THE  PDF");
        }
        return outputStream.toByteArray();
    }

    @Override
    public Invoice get_invoice(String Order_id) {
        // TODO Auto-generated method stub

        Optional<Orders> optional =Optional.of(get_Order(Order_id));
        Optional<Invoice> optional2 =  invoiceRepositry.findByOrders(optional.get());
        if(!optional2.isPresent()) {
            throw  new   Invoice_Not_Found("There  is  Invoice  to  that   Order");
        }
        return  optional2.get();
    }

    @Override
    public Orders get_Order(String Order_id) {
        // TODO Auto-generated method stub
        Optional<Orders> optional   =  orderRepositry.findById(Order_id);
        if(!optional.isPresent()) {
            throw  new  OrderId_NotFOund("The   Order  WIth  the   Order  id  "+ Order_id +" Does Not Exist");
        }
        return  optional.get();
    }

}
