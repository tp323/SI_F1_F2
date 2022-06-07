package isel.sisinf.grp02.repo;

public interface IContext extends AutoCloseable {

    void beginTransaction();
    void commit();
    void flush();

    //ICountryRepository getCountries();
    //IStudentRepository getStudents();
    //ICourseRepository getCourses();

}
