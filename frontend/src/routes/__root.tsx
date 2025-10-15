import {createRootRouteWithContext, Outlet} from '@tanstack/react-router'
import type {AuthContext} from "../types/auth/AuthContext.ts";

interface RouterContext {
    auth: AuthContext
}

export const Route = createRootRouteWithContext<RouterContext>()({
    component: RootComponent,
})

function RootComponent() {
    return (
        <Outlet/>
    );
}
