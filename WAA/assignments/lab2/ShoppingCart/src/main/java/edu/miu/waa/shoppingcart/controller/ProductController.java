package edu.miu.waa.shoppingcart.controller;

import edu.miu.waa.shoppingcart.model.Product;
import edu.miu.waa.shoppingcart.model.ShoppingCartItem;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {

    private Map<String, Product> productList;
    private Map<String, ShoppingCartItem> cartList;
    private int totalShoppingCart;

    @GetMapping("/products")
    public ModelAndView getProducts(HttpSession session) {
        productList = (Map<String, Product>) session.getAttribute("productList");
        if (productList == null) {
            productList = new HashMap<>();
            session.setAttribute("productList", productList);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("productList", productList.values());
        return new ModelAndView("products", params);
    }

    @PostMapping("/addProduct")
    public ModelAndView addproduct(HttpSession session) {
        Map<String, Object> params = new HashMap<>();
        params.put("product", new Product());
        return new ModelAndView("addproduct", params);
    }

    @PostMapping("/add")
    public ModelAndView add( HttpSession session, @Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.setViewName("addProduct");
            return mav;
        }

        Map<String, Object> params = new HashMap<>();
        if (product != null) {
            productList = (Map<String, Product>) session.getAttribute("productList");
            if (productList == null) {
                productList = new HashMap<>();
                session.setAttribute("productList", productList);
            }

            productList.put(product.getNumber(), product);
            params.put("productList", productList.values());

        }
        return new ModelAndView("redirect:products", params);
    }

    @PostMapping("/removeproduct")
    public ModelAndView remove(@RequestParam("number") String number, HttpSession session) {
        Map<String, Object> params = new HashMap<>();

        if (number != null) {
            productList = (Map<String, Product>) session.getAttribute("productList");
            if (productList == null) {
                productList = new HashMap<>();
                session.setAttribute("productList", productList);
            }

            productList.remove(number);
            params.put("productList", productList.values());
        }
        return new ModelAndView("products", params);
    }

    @PostMapping("/addProductCart")
    public ModelAndView addProductCart(@RequestParam("number") String number, HttpSession session) {
        Map<String, Object> params = new HashMap<>();

        if (number != null) {
            cartList = (Map<String, ShoppingCartItem>) session.getAttribute("cartList");
            if (cartList == null) {
                cartList = new HashMap<>();
                session.setAttribute("cartList", cartList);
            }

            if(cartList.containsKey(number)){
                ShoppingCartItem updatedCartItem = cartList.get(number);
                updatedCartItem.setQuantity(updatedCartItem.getQuantity() + 1);
                cartList.put(number, updatedCartItem);
            }else{
                Product product = getProductByNumber(number);
                if(product != null)
                    cartList.put(number, new ShoppingCartItem(1, product));
            }
            params.put("cartList", cartList.values());
        }
        return new ModelAndView("redirect:showcart", params);
    }

    private Product getProductByNumber(String number){
        return productList.get(number);
    }

    @GetMapping("/showcart")
    public ModelAndView showCart(HttpSession session) {
        cartList = (Map<String, ShoppingCartItem>) session.getAttribute("cartList");
        if (cartList == null) {
            cartList = new HashMap<>();
            session.setAttribute("cartList", cartList);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("cartList", cartList.values());
        params.put("totalShoppingCart",cartList.values().stream().mapToDouble(i -> i.getTotal()).sum());
        return new ModelAndView("shoppingcart", params);
    }

    @PostMapping("/removeproductcart")
    public ModelAndView removeProductCart(@RequestParam("number") String number, HttpSession session) {
        Map<String, Object> params = new HashMap<>();

        if (number != null) {
            cartList = (Map<String, ShoppingCartItem>) session.getAttribute("cartList");
            if (cartList == null) {
                cartList = new HashMap<>();
                session.setAttribute("cartList", cartList);
            }

            ShoppingCartItem item = cartList.get(number);

            if(item.getQuantity() == 1)
                cartList.remove(number);
            else{
                item.setQuantity(item.getQuantity()-1);
                cartList.put(number, item);
            }
            params.put("cartList", cartList.values());
        }
        return new ModelAndView("redirect:showcart", params);
    }
}

