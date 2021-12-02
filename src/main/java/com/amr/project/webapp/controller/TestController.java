//package com.amr.project.webapp.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.logging.Logger;
//
//@Controller
//@RequestMapping(value = "/upload")
//public class TestController {
//    private final Logger LOGGER = Logger.getLogger("<-------- LOG: Controller -------->");
//
//    private static String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");
//
//    @GetMapping("/")
//    public String index() {
//        return "upload";
//    }
//
//    @PostMapping("/upload") // //new annotation since 4.3
//    public String singleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//
//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "redirect:uploadStatus";
//        }
//
//        try {
//
//            // Get the file and save it somewhere
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//            Files.write(path, bytes);
//
//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(file.getOriginalFilename());
//        return "redirect:/uploadStatus";
//    }
//
//    @GetMapping("/uploadStatus")
//    public String uploadStatus() {
//        return "uploadStatus";
//    }
//}

