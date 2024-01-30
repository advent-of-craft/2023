import {User} from "../user/User";
import Trip from "./Trip";
import TripDAO from "./TripDAO";

export class TripRepository {
    findTripsByUser = (user: User): Trip[] => TripDAO.findTripsByUser(user);
}