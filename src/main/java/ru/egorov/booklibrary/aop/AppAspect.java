package ru.egorov.booklibrary.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.domain.entity.Genre;

@Component
@Aspect
@Slf4j
public class AppAspect {

    @Around("Pointcuts.allGetMethodsInAuthorService()")
    public Object aroundGetMethodInAuthorService(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        if (methodSignature.getName().equals("getById")) {
            Long authorId = ((Long) args[0]);
            log.info("Attempt to get author by id: {}", authorId);
        } else if (methodSignature.getName().equals("getAll")) {
            Integer pageNumber = (Integer) args[0];
            Integer pageSize = (Integer) args[1];
            String sortBy = (String) args[2];
            String sortDir = (String) args[3];

            log.info("Attempt to get all authors with parameters: pageNumber {}, pageSize {}, sortBy {}, sortDir {}", pageNumber, pageSize, sortBy, sortDir);
        } else if (methodSignature.getMethod().equals("getAllByFirstName")) {
            String firstName = (String) args[0];

            log.info("Attempt to get author with first name: {}", firstName);
        } else if (methodSignature.getMethod().equals("getAllBySecondName")) {
            String secondName = (String) args[0];

            log.info("Attempt to get author with second name: {}", secondName);
        }

        return proceedJoinPoint(joinPoint);
    }

    @Around("Pointcuts.allGetMethodsInBookService()")
    public Object aroundGetMethodInBookService(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        if (methodSignature.getMethod().equals("getById")) {
            Long bookId = ((Long) args[0]);
            log.info("Attempt to get book by id: {}", bookId);
        } else if (methodSignature.getMethod().equals("getAll")) {
            Integer pageNumber = (Integer) args[0];
            Integer pageSize = (Integer) args[1];
            String sortBy = (String) args[2];
            String sortDir = (String) args[3];

            log.info("Attempt to get all book with parameters: pageNumber {}, pageSize {}, sortBy {}, sortDir {}", pageNumber, pageSize, sortBy, sortDir);
        } else if (methodSignature.getMethod().equals("getAllByTitle")) {
            String bookTitle = ((String) args[0]);
            log.info("Attempt to get book by title: {}", bookTitle);
        } else if (methodSignature.getMethod().equals("getAllByYearOfIssue")) {
            Integer year = (Integer) args[0];
            String cmpr = (String) args[1];
            String sort = (String) args[2];

            log.info("Attempt to get all book by year of issue with parameters: year {}, compare by {}, sort {}", year, cmpr, sort);
        } else if (methodSignature.getMethod().equals("getAllByNumberOfPages")) {
            Integer year = (Integer) args[0];
            String cmpr = (String) args[1];
            String sort = (String) args[2];

            log.info("Attempt to get all book by number of pages with parameters: year {}, compare by {}, sort {}", year, cmpr, sort);
        } else if (methodSignature.getMethod().equals("getAllByAuthorId")) {
            Long authorId = ((Long) args[0]);
            log.info("Attempt to get books by author id: {}", authorId);
        } else if (methodSignature.getMethod().equals("getAllByGenreId")) {
            Long genreId = ((Long) args[0]);
            log.info("Attempt to get books by genre id: {}", genreId);
        }

        return proceedJoinPoint(joinPoint);
    }

    @Around("Pointcuts.allGetMethodsInGenreService()")
    public Object aroundGetMethodInGenreService(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        if (methodSignature.getMethod().equals("getById")) {
            Long genreId = ((Long) args[0]);
            log.info("Attempt to get genre by id: {}", genreId);
        } else if (methodSignature.getMethod().equals("getAll")) {
            Integer pageNumber = (Integer) args[0];
            Integer pageSize = (Integer) args[1];
            String sortBy = (String) args[2];
            String sortDir = (String) args[3];

            log.info("Attempt to get all genres with parameters: pageNumber {}, pageSize {}, sortBy {}, sortDir {}", pageNumber, pageSize, sortBy, sortDir);
        } else if (methodSignature.getMethod().equals("getByName")) {
            String genreName = ((String) args[0]);
            log.info("Attempt to get genre by name: {}", genreName);
        }

        return proceedJoinPoint(joinPoint);
    }

    @Around("Pointcuts.allUpdateMethods()")
    public Object aroundAllUpdateMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().toString();

        if (className.contains("Author")) {
            Author author = (Author) args[0];
            log.info("Attempt to update author with id {}", author.getId());
        } else if (className.contains("Book")) {
            Book book = (Book) args[0];
            log.info("Attempt to update book with id {}", book.getId());
        } else if (className.contains("Genre")) {
            Genre genre = (Genre) args[0];
            log.info("Attempt to update genre with id {}", genre.getId());
        }

        return proceedJoinPoint(joinPoint);
    }

    @Around("Pointcuts.allSaveMethods()")
    public Object aroundAllSaveMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().toString();

        if (className.contains("Author")) {
            Author author = (Author) args[0];
            log.info("Attempt to save author");
        } else if (className.contains("Book")) {
            Book book = (Book) args[0];
            log.info("Attempt to save book");
        } else if (className.contains("Genre")) {
            Genre genre = (Genre) args[0];
            log.info("Attempt to save genre");
        }

        return proceedJoinPoint(joinPoint);
    }

    @Around("Pointcuts.allDeleteMethods()")
    public Object aroundAllDeleteMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().toString();

        if (className.contains("Author")) {
            Author author = (Author) args[0];
            log.info("Attempt to delete author with id {}", author.getId());
        } else if (className.contains("Book")) {
            Book book = (Book) args[0];
            log.info("Attempt to delete book with id {}", book.getId());
        } else if (className.contains("Genre")) {
            Genre genre = (Genre) args[0];
            log.info("Attempt to delete genre with id {}", genre.getId());
        }

        return proceedJoinPoint(joinPoint);
    }

    private Object proceedJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        log.info("The method was completed successfully");
        return result;
    }
}
