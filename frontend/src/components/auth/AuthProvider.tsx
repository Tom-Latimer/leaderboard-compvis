import React, {createContext, useContext, useState} from "react";
import * as authApi from '../../api/auth/authApi.ts';
import type {UserPrincipal} from "../../types/auth/authTypes.ts";
import type {AuthContext} from "../../types/auth/AuthContext.ts";
import {setToken} from "../../api/auth/authToken.ts";

const AuthContext = createContext<AuthContext | null>(null);

const AuthProvider = ({children}: { children: React.ReactNode }) => {
    const [token, setTokenState] = useState<string | null>(null);
    const [user, setUser] = useState<UserPrincipal | null>(null);

    const login = React.useCallback(async (email: string, password: string) => {
        const {token, user} = await authApi.login({email, password});

        setToken(token);
        setTokenState(token);
        setUser(user);
    }, []);

    const logout = React.useCallback(() => {
        setToken(null);
        setTokenState(null);
        setUser(null);
    }, []);

    return (
        <AuthContext.Provider value={{token, user, isAuthenticated: !!token, login, logout}}>
            {children}
        </AuthContext.Provider>
    );
}

export default AuthProvider;

export const useAuth = () => {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider.");
    }

    return context;
}