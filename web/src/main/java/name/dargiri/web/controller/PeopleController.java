package name.dargiri.web.controller;

import name.dargiri.data.dto.PersonDTO;
import name.dargiri.data.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = {"", "/all"})
    public ModelAndView all() {
        ModelAndView mav = new LayoutModelAndView("main/people");
        List<PersonForm> all = personService.findAll().stream().map(PersonForm::from).collect(Collectors.<PersonForm>toList());
        mav.addObject("people", all);
        return mav;
    }

    @RequestMapping(value = "/create")
    public ModelAndView createForm()

    {
        ModelAndView mav = new LayoutModelAndView("main/person");
        mav.addObject("person", new PersonForm());
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(PersonForm personForm)

    {
        personService.create(toDTO(personForm));
        return new ModelAndView("redirect:/people/all");
    }

    private PersonDTO toDTO(PersonForm personForm)

    {
        return new PersonDTO(personForm.getId(), personForm.getUsername(), null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editForm(@PathVariable("id") UUID id) {
        PersonDTO dto = personService.find(id);
        if (dto != null) {
            ModelAndView mav = new LayoutModelAndView("main/person");
            mav.addObject("person", PersonForm.from(dto));
            return mav;
        } else {
            return new ModelAndView("redirect:/");
        }
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView edit(PersonForm personForm) {
        PersonDTO person = toDTO(personForm);
        PersonDTO updated = personService.update(person);
        if (updated != null) {
            return new ModelAndView("redirect:/people/all");
        } else {
            ModelAndView mav = new LayoutModelAndView("main/person");
            mav.addObject("person", personForm);
            return mav;
        }
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") UUID id) {
        personService.delete(id);
        return new ModelAndView("redirect:/people/all");
    }

    public static class PersonForm {
        public static PersonForm from(PersonDTO dto) {
            PersonForm form = new PersonForm();
            form.setId(dto.id);
            form.setUsername(dto.name);
            form.setSurname(dto.surname);
            form.setJob(dto.jobPosition);
            form.setSex(dto.sex);
            form.setDob(dto.dob);
            form.setAboutMe(dto.aboutMe);
            return form;
        }

        public UUID id;
        public String name;
        public String surname;
        public String sex;
        public String dob;
        public String mobilePhone;
        public String job;
        public String aboutMe;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getUsername() {
            return name;
        }

        public void setUsername(String username) {
            this.name = username;
        }

        public String getName() {
            return name;
        }

        public String getAboutMe() {
            return aboutMe;
        }

        public void setAboutMe(String aboutMe) {
            this.aboutMe = aboutMe;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }
    }
}
