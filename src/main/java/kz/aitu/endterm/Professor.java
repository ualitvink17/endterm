package kz.aitu.endterm;

public class Professor {
    private Long id;
    private String name;
    private String department;
    private String city;
    private Integer experienceYears;

    public Professor() {}
    public Professor(Long id, String name, String department, String city, Integer experienceYears) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.city = city;
        this.experienceYears = experienceYears;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
}