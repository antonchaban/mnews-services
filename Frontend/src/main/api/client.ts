import axios from "axios";
import Endpoints from "./endpoints"
export default axios.create({
    baseURL: Endpoints.API_URL
});