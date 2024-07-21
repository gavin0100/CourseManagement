package com.example.back_end_fams.controller;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.UI.CalendarRequestUI;
import com.example.back_end_fams.model.response.ClassDateResponse;
import com.example.back_end_fams.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private ClassService classService;
    private String message = null;
    private String errorMessage = null;

    private List<ClassDateResponse> classDateResponseListGlobal;
    @GetMapping
    public String getCalendar(Model model) throws ParseException {
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        Date date = new Date();

        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date dateString = inputFormat.parse(date.toString());

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String outputDateString = outputFormat.format(dateString);
        outputDateString = "\"" + outputDateString + "\"";

        List<ClassDateResponse> classDateResponseList = classService.getAllClassDate();
        classDateResponseListGlobal = classDateResponseList;
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("date", outputDateString);
        model.addAttribute("mode", "Week");
        message = null;
        errorMessage = null;
        return "ui_calendar";
    }
    @PostMapping("/filter")
    public String getCalendar(
            @ModelAttribute CalendarRequestUI calendarRequestUI,
            Model model) throws ParseException {
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        String searchWord = calendarRequestUI.getSearch();
        String status = calendarRequestUI.getStatus();
        String partOfDateMorning = calendarRequestUI.getPartOfDateMorning();
        String partOfDateNoon = calendarRequestUI.getPartOfDateNoon();
        String partOfDateNight = calendarRequestUI.getPartOfDateNight();
        String chosenDate = calendarRequestUI.getChosenDate();
        String mode = calendarRequestUI.getMode();

        if (searchWord == null){
            searchWord = "";
        }
        if (status == null){
            status = "";
        }if (searchWord == null){
            searchWord = "";
        }
        if (partOfDateMorning == null){
            partOfDateMorning = "";
        }
        if (partOfDateNoon == null){
            partOfDateNoon = "";
        }
        if (partOfDateNight == null){
            partOfDateNight = "";
        }
        if (mode == null){
            mode = "Week";
        }

        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (chosenDate.startsWith("\"")){
            chosenDate = chosenDate.substring(1, chosenDate.length() - 1);
        }
        date = dateFormat.parse(chosenDate);
        String dateActive = new SimpleDateFormat("yyyy-MM-dd").format(date).toString();
        List<ClassDateResponse> classDateResponseListMorning = new ArrayList<>();
        List<ClassDateResponse> classDateResponseListNoon = new ArrayList<>();
        List<ClassDateResponse> classDateResponseListNight = new ArrayList<>();
        if (partOfDateMorning.equals("") && partOfDateNoon.equals("") && partOfDateNight.equals("")){
            classDateResponseListMorning = classService.getClassDateListByPartOfDay(this.classDateResponseListGlobal, "Morning", searchWord, status, date, mode);
            classDateResponseListNoon = classService.getClassDateListByPartOfDay(this.classDateResponseListGlobal, "Noon", searchWord, status, date, mode);
            classDateResponseListNight = classService.getClassDateListByPartOfDay(this.classDateResponseListGlobal, "Night", searchWord, status, date, mode);
        }
        if (partOfDateMorning.equals("Morning")){
            classDateResponseListMorning = classService.getClassDateListByPartOfDay(this.classDateResponseListGlobal, "Morning", searchWord, status, date,mode);
        }
        if (partOfDateNoon.equals("Noon")){
            classDateResponseListNoon = classService.getClassDateListByPartOfDay(this.classDateResponseListGlobal, "Noon", searchWord, status, date,mode);
        }
        if (partOfDateNight.equals("Night")){
            classDateResponseListNight = classService.getClassDateListByPartOfDay(this.classDateResponseListGlobal, "Night", searchWord, status, date, mode);
        }

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("classDateResponseListMorning", classDateResponseListMorning);
        model.addAttribute("classDateResponseListNoon", classDateResponseListNoon);
        model.addAttribute("classDateResponseListNight", classDateResponseListNight);
        model.addAttribute("date", chosenDate);
        model.addAttribute("dateActive", dateActive);
        model.addAttribute("mode", mode);
        model.addAttribute("search", searchWord);
        model.addAttribute("status", status);
        model.addAttribute("partOfDateMorning", partOfDateMorning);
        model.addAttribute("partOfDateNoon", partOfDateNoon);
        model.addAttribute("partOfDateNight", partOfDateNight);
        model.addAttribute("chosenDate", chosenDate);
        model.addAttribute("mode", mode);

        message = null;
        errorMessage = null;
        return "ui_calendar";
    }
}




