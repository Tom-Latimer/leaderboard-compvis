import React from "react";
import {Link, type LinkProps} from "@tanstack/react-router";

type NavLinkProps = LinkProps & {
    icon: React.ReactNode;
    children: React.ReactNode;
}

const NavLink = ({icon, children, ...rest}: NavLinkProps) => {
    return (
        <Link className="nav-link" activeProps={{className: ""}} {...rest}>
            <span className="nav-link-icon">
                {icon}
            </span>
            <span className="nav-link-text">
                {children}
            </span>
        </Link>
    );
}

const NavSection = ({heading, children}: { heading: string, children: React.ReactNode }) => {
    return (
        <div className="nav-section">
            <h5 className="nav-section-heading">{heading}</h5>
            <div className="nav-section-content">
                {children}
            </div>
        </div>
    );
}

const AdminSidebar = () => {
    return (
        <nav>
            <NavSection heading="">
                <NavLink icon={}>

                </NavLink>
            </NavSection>
        </nav>
    );
}

export default AdminSidebar;