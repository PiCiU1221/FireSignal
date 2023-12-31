import { useEffect, useState } from "react";
import jwtDecode from "jwt-decode";
import Cookies from "js-cookie";
import axios from "axios";

interface JwtPayload {
  sub: string;
  exp: number;
  iat: number;
}

interface SidebarProps {
  activeMenu: string;
  handleMenuClick: (menu: string) => void;
  handleLogout: () => void;
  handleLoadingComplete: () => void;
}

const Sidebar: React.FC<SidebarProps> = ({
  activeMenu,
  handleMenuClick,
  handleLogout,
  handleLoadingComplete,
}) => {
  // Use state to manage subject and initialize it to an empty string
  const [username, setUsername] = useState<string>("");
  const [userRole, setUserRole] = useState<string>("");

  const fetchUserRole = async (username: string) => {
    const apiBaseUrl = process.env.NEXT_PUBLIC_API_BASE_URL;

    // Fetch user role from the backend API
    try {
      const response = await axios.get(
        `${apiBaseUrl}/api/auth/user-role?username=${username}`,
        {
          headers: {
            Authorization: `Bearer ${Cookies.get("token")}`,
          },
        }
      );

      if (response.data.role) {
        setUserRole(response.data.role);
      }
    } catch (error) {
      console.error("Error during fetching user role:", error);
    } finally {
      handleLoadingComplete();
    }
  };

  // Define a function to fetch and set the username from cookies
  const fetchUsernameFromCookies = () => {
    const token = Cookies.get("token");
    const decodedToken = token ? jwtDecode<JwtPayload>(token) : null;
    const username = decodedToken ? decodedToken.sub : "";
    setUsername(username);

    fetchUserRole(username);
  };

  // Use useEffect to fetch subject on the client side after initial render
  useEffect(() => {
    fetchUsernameFromCookies();
  }, []); // Empty dependency array means this effect runs after the initial render

  useEffect(() => {
    if (userRole === "ADMIN") {
      handleMenuClick("new-alarm");
    }
  }, [userRole]);

  return (
    <aside className="bg-gray-800 h-screen flex flex-col p-6">
      {/* Logo and Company Name */}
      <div className="flex items-center mb-6 text-2xl font-semibold text-white">
        <img className="w-16 h-16 mr-2" src="/siren_logo.svg" alt="logo"></img>
        FireSignal
      </div>

      {/* Menu Options */}
      <nav className="flex-1">
        <ul className="space-y-4 mt-4">
          {userRole == "ADMIN" && (
            <li
              onClick={() => handleMenuClick("new-alarm")}
              className={`px-4 py-2 text-white ${
                activeMenu === "new-alarm" ? "bg-primary-500" : ""
              } hover:bg-primary-500 hover:text-white cursor-pointer`}
            >
              New Alarm
            </li>
          )}
          <li
            onClick={() => handleMenuClick("alarms")}
            className={`px-4 py-2 text-white ${
              activeMenu === "alarms" ? "bg-primary-500" : ""
            } hover:bg-primary-500 hover:text-white cursor-pointer`}
          >
            Alarms
          </li>
          {userRole == "ADMIN" && (
            <li
              onClick={() => handleMenuClick("fire-departments")}
              className={`px-4 py-2 text-white ${
                activeMenu === "fire-departments" ? "bg-primary-500" : ""
              } hover:bg-primary-500 hover:text-white cursor-pointer`}
            >
              Fire Departments
            </li>
          )}
          <li
            onClick={() => handleMenuClick("fire-engines")}
            className={`px-4 py-2 text-white ${
              activeMenu === "fire-engines" ? "bg-primary-500" : ""
            } hover:bg-primary-500 hover:text-white cursor-pointer`}
          >
            Fire Engines
          </li>
          <li
            onClick={() => handleMenuClick("firefighters")}
            className={`px-4 py-2 text-white ${
              activeMenu === "firefighters" ? "bg-primary-500" : ""
            } hover:bg-primary-500 hover:text-white cursor-pointer`}
          >
            Firefighters
          </li>
        </ul>
      </nav>

      {/* Display Username */}
      {username && ( // Render only if username is available
        <div className="text-white text-sm text-center font-semibold mb-1">
          Welcome, {username}
        </div>
      )}

      {/* Logout Button */}
      <button
        onClick={handleLogout}
        className="px-4 py-2 mt-4 text-white bg-red-700 hover:bg-red-800 font-semibold"
      >
        Logout
      </button>
    </aside>
  );
};

export default Sidebar;
