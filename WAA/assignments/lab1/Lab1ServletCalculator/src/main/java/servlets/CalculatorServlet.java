package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/calc")
public class CalculatorServlet extends HttpServlet {

    List<List<String>> matrix = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //show calculator page

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>SIMPLE CALCULATOR<br><br><br>  <style> table, th, td { border: 1px solid black; width:150px; text-align: center;}</style>  </head>");
        out.println("<body>");
        out.println("<form method = 'post' action = 'calc'>");
        out.println("enter the first number:<br>");
        out.println("<input type = 'text' name='number1'><br><br>");
        out.println("enter the second number:<br>");
        out.println("<input type = 'text' name='number2'><br><br>");
        out.println("enter the operation:<br><br>");
        out.println("<input type ='radio' name = 'op' value = '+'>add<br>");
        out.println("<input type = 'radio' name = 'op' value = '-'>sub<br>");
        out.println("<input type = 'radio' name = 'op' value = '*'>mul<br>");
        out.println("<input type = 'radio' name = 'op' value = '/'>div<br><br>");
        out.println("<input type = 'submit' name = 'result' value = 'submit'><br>");

        try {
            String number1String = request.getParameter("number1");
            String number2String = request.getParameter("number2");
            Double number1 = null, number2 = null;

            if(number1String.isEmpty()) throw new InvalidObjectException("Fill out number 1");
            if(number2String.isEmpty()) throw new InvalidObjectException("Fill out number 2");

            number1 = Double.valueOf(number1String);
            number2 = Double.valueOf(number2String);

            String op = request.getParameter("op");
            if(op == null) throw new InvalidObjectException("Select an operation");

            Double result = null;
            switch (op){
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
                case "/":
                    result = number1 / number2;
                    break;
            }
            out.println("<br><br><h3>The result of "+ number1+ " "+ op +" "+ number2+" = "+result+"</h3>");
            matrix.add(new ArrayList<>(Arrays.asList(number1String, op, number2String, String.valueOf(result))));

            if(matrix.size() > 1){

                out.println("<br><br> <table>  <th>first</th>  <th>operation</th>  <th>second</th>  <th>result</th> ");

                for (List l: matrix) {
                   out.println(" <tr> <td>"+l.get(0)+"</td> <td>"+l.get(1)+"</td> <td>"+l.get(2)+"</td> <td>"+l.get(3)+"</td> </tr> ");
                }
                out.println("</table>");
            }

        }catch (Exception e){
            if(e instanceof InvalidObjectException)
                out.println("<br><br> Error: "+e.getMessage());
            else if (e instanceof NumberFormatException)
                out.println("<br><br> Error: Number invalid");
        }

        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //show result page
        doGet(request,response);
    }

}
