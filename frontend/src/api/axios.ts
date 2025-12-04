import axios from 'axios';
import {getToken} from "./auth/authToken.ts";
import {notFound} from "@tanstack/react-router";
import {getRouter} from "../router.tsx";

//global axios instance with auth header
const api = axios.create({
    baseURL: import.meta.env.VITE_BASE_API_URL + '/api',
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

//api used for admin queries
export const adminApi = axios.create({
    baseURL: import.meta.env.VITE_BASE_API_URL + '/api',
});

adminApi.interceptors.request.use((config) => {

    //add JWT auth token to request if present
    const token = getToken();
    if (token) config.headers.Authorization = `Bearer ${token}`;

    return config;
});

adminApi.interceptors.response.use(
    function onFulfilled(response) {

        //if the request succeeds, just return the response
        return response;
    }, function onRejected(error) {

        if (error.response) {

            const {status} = error.response;

            //handle http errors
            if (axios.isAxiosError(error)) {

                if (status === 401 || status === 403) {

                    const router = getRouter();

                    router.navigate({
                        to: "/login"
                    });

                    //throw non-resolving promise to avoid error from propagating
                    return new Promise(() => {
                    });
                }

                //trigger tanstack not found page if 404 error
                if (status === 404) {
                    throw notFound();
                }
            }
        }


        return Promise.reject(error);
    }
);