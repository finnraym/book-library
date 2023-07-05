package ru.egorov.booklibrary.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* ru.egorov.booklibrary.service.AuthorService.get*(..))")
    public void allGetMethodsInAuthorService() {}

    @Pointcut("execution(* ru.egorov.booklibrary.service.BookService.get*(..))")
    public void allGetMethodsInBookService() {}

    @Pointcut("execution(* ru.egorov.booklibrary.service.GenreService.get*(..))")
    public void allGetMethodsInGenreService() {}

    @Pointcut("execution(* ru.egorov.booklibrary.service.*.update*(..))")
    public void allUpdateMethods() {}

    @Pointcut("execution(* ru.egorov.booklibrary.service.*.save*(..))")
    public void allSaveMethods() {}

    @Pointcut("execution(* ru.egorov.booklibrary.service.*.delete*(..))")
    public void allDeleteMethods() {}
}
