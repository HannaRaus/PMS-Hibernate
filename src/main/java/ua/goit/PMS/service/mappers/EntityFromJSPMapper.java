package ua.goit.PMS.service.mappers;

import javax.servlet.http.HttpServletRequest;

public interface EntityFromJSPMapper<T> {

    T readJSPForm(HttpServletRequest req);

    T readJSPForm(HttpServletRequest req, T entity);
}
