import './navbar.css'
import '../../styles/utility.css'
import {Link} from "@tanstack/react-router";

const Navbar = () => {

    return (
        <div className="nav-container">
            <nav className="navbar flex justify-between">
                <div className="flex justify-between gap-1">
                    <Link to="/" className="nav-link" activeProps={{className: "nav-link-active"}}>Home</Link>
                    <Link to="/challenges" className="nav-link"
                          activeProps={{className: "nav-link-active"}}>Challenges</Link>
                </div>
            </nav>
        </div>
    );
}

export default Navbar;