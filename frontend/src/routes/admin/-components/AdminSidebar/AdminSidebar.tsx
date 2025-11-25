import React from "react";
import {Link, type LinkProps} from "@tanstack/react-router";
import FileIcon from "../../../../assets/file.svg?react";
import "./admin-sidebar.css";

type NavLinkProps = LinkProps & {
    icon: React.ReactNode;
    children: React.ReactNode;
}

const NavLink = ({icon, children, ...rest}: NavLinkProps) => {
    return (
        <Link className="nav-link" activeProps={{className: "active"}} {...rest}>
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
        <nav className="sidebar-nav">
            <NavSection heading="Main">
                <NavLink to="/admin/submissions" icon={<FileIcon/>}>
                    Submissions
                </NavLink>
            </NavSection>
        </nav>
    );
}

export default AdminSidebar;