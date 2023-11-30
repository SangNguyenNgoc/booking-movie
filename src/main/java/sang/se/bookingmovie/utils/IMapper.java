package sang.se.bookingmovie.utils;

public interface IMapper<Entity, Request, Response> {

    Entity requestToEntity(Request request);

    Response entityToResponse(Entity entity);
}
