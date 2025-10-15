import './navbar.css'
import '../../styles/utility.css'
import {Link} from "@tanstack/react-router";
import {useAuth} from "../auth/AuthProvider.tsx";

const AccountMenu = () => {
    const {isAuthenticated, logout} = useAuth();

    if (isAuthenticated) {
        return (
            <div className="flex justify-between gap-1">
                <Link to="/profile" className="nav-link" activeProps={{className: "nav-link-active"}}>Profile</Link>
                <button className="nav-button" onClick={logout}>Log Out</button>
            </div>
        );
    }

    return (
        <div className="flex justify-between">
            <Link to="/login" className="nav-link" activeProps={{className: "nav-link-active"}}>Login</Link>
            <Link to="/signup" className="nav-link" activeProps={{className: "nav-link-active"}}>Sign Up</Link>
        </div>
    );
}

const Navbar = () => {

    return (
        <div className="nav-container">
            <nav className="navbar flex justify-between">
                <div className="flex justify-between gap-1">
                    <Link to="/" className="nav-link" activeProps={{className: "nav-link-active"}}>Home</Link>
                    <Link to="/challenges" className="nav-link"
                          activeProps={{className: "nav-link-active"}}>Challenges</Link>
                </div>
                <AccountMenu/>
            </nav>
        </div>
    );
}

export default Navbar;