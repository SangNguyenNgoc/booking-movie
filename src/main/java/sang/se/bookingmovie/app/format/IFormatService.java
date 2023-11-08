package sang.se.bookingmovie.app.format;

import sang.se.bookingmovie.response.ListResponse;


public interface IFormatService {

    String create(Format format);

    ListResponse getAll();
}
