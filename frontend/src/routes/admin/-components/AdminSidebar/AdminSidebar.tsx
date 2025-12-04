import React from "react";
import {Link, type LinkProps, useNavigate, useRouter} from "@tanstack/react-router";
import {useAuth} from "../../../../components/auth/AuthProvider.tsx";
import FileIcon from "../../../../assets/file.svg?react";
import SignInIcon from "../../../../assets/sign-in.svg?react";
import SignOutIcon from "../../../../assets/sign-out.svg?react";
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

    const router = useRouter();
    const navigate = useNavigate();

    const {isAuthenticated, logout} = useAuth();

    const handleLogout = () => {

        //clear auth state
        logout();

        //clear cached routes
        router.invalidate().finally(() => {
            navigate({to: "/login"});
        });
    }

    return (
        <nav className="sidebar-nav">
            <div className="sidebar-nav-content">
                <NavSection heading="Main">
                    <NavLink to="/admin/submissions" icon={<FileIcon/>}>
                        Submissions
                    </NavLink>
                </NavSection>
            </div>
            <div className="nav-login-section">
                {isAuthenticated ? (
                    <button className="nav-link nav-btn" onClick={handleLogout}>
                        <span className="nav-link-icon">
                            <SignOutIcon/>
                        </span>
                        <span className="nav-link-text">
                            Log Out
                         </span>
                    </button>
                ) : (
                    <NavLink to="/login" icon={<SignInIcon/>}>
                        Log In
                    </NavLink>
                )}
            </div>
        </nav>
    );
}

export default AdminSidebar;