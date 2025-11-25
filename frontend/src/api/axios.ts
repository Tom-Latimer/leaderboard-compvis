import axios from 'axios';
import {getToken} from "./auth/authToken.ts";
import {notFound} from "@tanstack/react-router";

//global axios instance with auth header
const api = axios.create({
    baseURL: import.meta.env.VITE_BASE_API_URL + '/api',
});

api.interceptors.request.use((config) => {

    //add JWT auth token to request if present
    const token = getToken();
    if (token) config.headers.Authorization = `Bearer ${token}`;

    return config;
});

api.interceptors.response.use(
    function onFulfilled(response) {

        //if the request succeeds, just return the response
        return response;
    }, function onRejected(error) {

        //trigger tanstack not found page if 404 error
        if (axios.isAxiosError(error) && error.response) {
            if (error.response.status === 404) {
                throw notFound();
            }
        }

        return Promise.reject(error);
    }
);

export default api;