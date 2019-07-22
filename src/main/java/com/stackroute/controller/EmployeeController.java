package com.stackroute.controller;

import com.stackroute.model.Employee;
import com.stackroute.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
class EmployeeController {
    @Autowired
    EmployeeDao dao;//will inject dao from XML file

    /*It displays a form to input data, here "command" is a reserved request attribute
     *which is used to display object data into form
     */
    @RequestMapping("/empform")
    public String showform(Model m){
        m.addAttribute("command", new Employee());
        return "empform";
    }
    /*It saves object into database. The @ModelAttribute puts request data
     *  into model object. You need to mention RequestMethod.POST method
     *  because default request is GET*/
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String save(@ModelAttribute("employee") Employee employee){
        dao.save(employee);
        return "redirect:/viewemp";//will redirect to viewemp request mapping
    }
    /* It provides list of employees in model object */
    @RequestMapping("/viewemp")
    public String viewemp(Model m){
        List<Employee> list=dao.getEmployees();
        System.out.println(list.size());
        m.addAttribute("list",list);

        return "viewemp";
    }
    /* It displays object data into form for the given id.
     * The @PathVariable puts URL data into variable.*/
    @RequestMapping(value="/editemp/{id}")
    public String edit(@PathVariable int id, Model m){
        Employee employee =dao.getEmpById(id);
        m.addAttribute("command", employee);
        return "empeditform";
    }
    /* It updates model object. */
    @RequestMapping(value="/editemp/editsave",method = RequestMethod.POST)
    public String editsave(@ModelAttribute("employee") Employee employee){
        dao.update(employee);
        System.out.println("yes edit");
        return "redirect:/viewemp";
    }
    /* It deletes record for the given id in URL and redirects to /viewemp */
    @RequestMapping(value="/deleteemp/{id}",method = RequestMethod.GET)
    public String delete(@PathVariable int id){
        dao.delete(id);
        System.out.println(id);
        System.out.println("yes delee");
        return "redirect:/viewemp";
    }
}