package com.ll.exam.sbb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    // get post 둘다
//    @RequestMapping("/sbb")
//    @ResponseBody
//    public String index(@RequestParam(defaultValue = "1") int age){
//        return "안녕잘가여";
//    }
//    @GetMapping("/page1")
//    @ResponseBody
//    public String showPage1() {
//        return """
//                <form method="POST" action="/page2">
//                    <input type="number" name="age" placeholder="나이" />
//                    <input type="submit" value="page2로 POST 방식으로 이동" />
//                </form>
//                """;
//    }
//
//    @PostMapping("/page2")
//    @ResponseBody
//    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
//        return """
//                <h1>입력된 나이 : %d</h1>
//                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
//                """.formatted(age);
//    }
//
//    @GetMapping("/plus")
//    @ResponseBody
//    public String plus(int a, int b){
//        return a+b+"";
//    }
//    @GetMapping("/minus")
//    @ResponseBody
//    public String minus(int a, int b){
//        return a-b+"";
//    }
//    private int aa = 0;
//    @GetMapping("/increase")
//    @ResponseBody
//    public String increase(){
//        return aa++ + "";
//    }
//
//    @GetMapping("/gugudan")
//    @ResponseBody
//    public String gugudan(@RequestParam(defaultValue = "1") int dan,
//                          @RequestParam(defaultValue = "1") int limit){
////        StringBuilder sb = new StringBuilder();
////        for (int i = 1; i <=limit; i++) {
////            sb.append("%d * %d = %d".formatted(dan,i,dan*i));
////            sb.append("<br>");
////        }
////        return sb.toString();
//
//        return IntStream.rangeClosed(1,limit)
//                .mapToObj(i -> "%d * %d = %d".formatted(dan,i,dan*i))
//                .collect(Collectors.joining("<br>"));
//    }
//    @GetMapping("/mbti/{name}")
//    @ResponseBody
//    public String mbti(@PathVariable String name){
//        return switch(name){
//            case "홀길동" -> "IDSL";
//            case "s홀길동" -> {
//                String a = "as";
//                yield a;
//            }
//            default -> "모름";
//        };
//    }
//
//
//    @GetMapping("/saveSessionAge/{id}")
//    @ResponseBody
//    public int saveSession(@PathVariable int id, HttpServletRequest request){
//        HttpSession session = request.getSession(); // 세션을 생성해서
//        //HttpSession session이걸 바로 매개변수로 넣을 수도 있음음        session.setAttribute("userid", id); // userid로 uid값을 넘기자
//        return id;
//    }
//
//    @GetMapping("/getSessionAge")
//    @ResponseBody
//    public int getSessionAge(HttpServletRequest request){
//        HttpSession session = request.getSession(); // 세션을 생성해서
////        session.invalidate();
//        return (int)session.getAttribute("userid");
//    }

    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/addArticle">
                    <input type="text" name="title" placeholder="제목" />
                    <input type="text" name="body" placeholder="내용" />
                    <input type="submit" value="POST 방식으로 이동" />
                </form>
                """;
    }
    public static List<ArticleDto> articleList = new ArrayList<>();
    @PostMapping("/addArticle")
    @ResponseBody
    public String addArticle(@RequestParam String title,
                             @RequestParam String body){
        ArticleDto articleDto = new ArticleDto(title, body);
        articleList.add(articleDto);
        return "%d번 글이 등록되었습니다".formatted(articleDto.getId());
    }
    @GetMapping("/article/{id}")
    @ResponseBody
    public ArticleDto getArticle(@PathVariable int id){
        for (ArticleDto articleDto : articleList) {
            if(articleDto.getId() == id){
                return articleDto;
            }
        }
        return null;
    }
    @GetMapping("/deleteArticle/{id}")
    @ResponseBody
    public String deleteArticle(@PathVariable int id){
        for (ArticleDto articleDto : articleList) {
            if(articleDto.getId() == id){
                articleList.remove(articleDto);
                return "%d번 글이 삭제되었습니다".formatted(id);
            }
        }
        return "%d번 글은 존재하지 않습니다".formatted(id);
    }
    @GetMapping("/modifyArticle")
    @ResponseBody
    public String modifyArticle(@RequestParam int id,
                                @RequestParam String title,
                                @RequestParam String body){
        for (ArticleDto articleDto : articleList) {
            if(articleDto.getId() == id){
                articleDto.setTitle(title);
                articleDto.setBody(body);
                return "%d번 글이 수정되었습니다".formatted(id);
            }
        }
        return "%d번 글은 존재하지 않습니다".formatted(id);
    }
    @GetMapping("/test")
    @ResponseBody
    public String test(@ModelAttribute("person") Person person){
        int age = person.getAge();
        int id = person.getId();
        String name = person.getName();

        return "id 는 %d : 나이는 %d : 이름은 %s".formatted(id,age,name);
    }
    @GetMapping("/test2/{id}")
    @ResponseBody
    public String test2(Person person){
        int age = person.getAge();
        int id = person.getId();
        String name = person.getName();

        return "id 는 %d : 나이는 %d : 이름은 %s".formatted(id,age,name);
    }
    @AllArgsConstructor
    @Data
    static class ArticleDto{
        private static int lastId = 0;
        private int id;
        private String title;
        private String body;
        public ArticleDto(String title, String body){
            this(++lastId,title,body);
        }

    }

}
@Data
@AllArgsConstructor
class Person{
    private int id;
    private int age;
    private String name;
}
