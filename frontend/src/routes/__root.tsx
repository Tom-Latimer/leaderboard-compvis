import {createRootRouteWithContext, Link, Outlet} from '@tanstack/react-router'
import type {AuthContext} from "../types/auth/AuthContext.ts";

interface RouterContext {
    auth: AuthContext
}

export const Route = createRootRouteWithContext<RouterContext>()({
    component: RootComponent,
    notFoundComponent: RootNotFoundComponent,
})

function RootComponent() {
    return (
        <Outlet/>
    );
}

function RootNotFoundComponent() {

    return (
        <div className="not-found-page">
            <h1 className="heading">
                404
            </h1>
            <h3 className="subheading">Page Not Found</h3>
            <p className="text">The page you are trying to access doesn't exist or has been moved.</p>
            <div className="link-button">
                <Link to="/">Go to Homepage</Link>
            </div>
        </div>
    );
}
