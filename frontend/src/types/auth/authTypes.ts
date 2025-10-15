export interface LoginRequest {
    email: string;
    password: string;
}

export interface SignUpRequest {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

export interface AuthResponse {
    token: string;
    user: UserPrincipal;
}

export interface UserPrincipal {
    email: string;
    firstName: string;
    lastName: string;
    roles: string[];
}