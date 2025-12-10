import {createRootRouteWithContext, Link, Outlet, redirect} from '@tanstack/react-router'
import type {AuthContext} from "../types/auth/AuthContext.ts";

interface RouterContext {
    auth: AuthContext
}

export const Route = createRootRouteWithContext<RouterContext>()({
    beforeLoad: ({location}) => {

        //redirect to challenges as there is no homepage
        //remove if you want to add a homepage
        if (location.pathname === "/") {
            throw redirect({
                to: "/challenges"
            });
        }
    },
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
                <Link to="/challenges">Go to Homepage</Link>
            </div>
        </div>
    );
}
