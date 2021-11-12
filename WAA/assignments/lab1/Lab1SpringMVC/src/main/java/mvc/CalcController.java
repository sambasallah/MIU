package mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.InvalidObjectException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CalcController {

    @RequestMapping("/calc")
    public ModelAndView calc(@RequestParam(value="num1") String number1String,
                             @RequestParam(value="num2") String number2String, @RequestParam(value="op") String op) {

        Map<String, Object> params = new HashMap<>();
        try{

            Double number1 = Double.parseDouble(number1String);
            Double number2 = Double.parseDouble(number2String);

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
                default:
                    throw new InvalidObjectException("Invalid operation");
            }

            params.put("number1", number1);
            params.put("op", op);
            params.put("number2", number2);
            params.put("result", result);

        } catch (Exception e){
            if(e instanceof InvalidObjectException)
                params.put("error", e.getMessage());
            else if (e instanceof NumberFormatException)
                params.put("error", "Input a valid number");
        }

        return new ModelAndView("calc",params);
    }
}

