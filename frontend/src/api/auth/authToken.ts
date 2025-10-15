let token: string | null = null;

export function setToken(t: string | null) {
    token = t;
}

export const getToken = () => token;