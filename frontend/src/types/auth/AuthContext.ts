import type {UserPrincipal} from "./authTypes.ts";

export interface AuthContext {
    isAuthenticated: boolean;
    token: string | null;
    user: UserPrincipal | null;
    login: (email: string, password: string) => Promise<void>;
    logout: () => void;
}