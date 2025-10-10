import './navbar.css'
import '../styles/utility.css'
import {Link} from "@tanstack/react-router";

const AccountMenu = ({isSignedIn}: { isSignedIn: boolean }) => {
    if (isSignedIn) {
        return (
            <div className="flex justify-between gap-1">
                <Link to="/profile" className="nav-link" activeProps={{className: "nav-link-active"}}>Profile</Link>
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
                <AccountMenu isSignedIn={true}/>
            </nav>
        </div>
    );
}

export default Navbar;