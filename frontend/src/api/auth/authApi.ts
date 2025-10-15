import type {AuthResponse, LoginRequest, SignUpRequest} from "../../types/auth/authTypes.ts";
import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_API_URL + '/api';

export const login = async (credentials: LoginRequest): Promise<AuthResponse> => {
    const result = await axios.post<AuthResponse>(`${BASE_URL}/auth/login`, credentials);
    return result.data;
}

export const signUp = async (userInfo: SignUpRequest): Promise<AuthResponse> => {
    const result = await axios.post<AuthResponse>(`${BASE_URL}/auth/signup`, userInfo);
    return result.data;
}