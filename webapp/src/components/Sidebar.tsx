interface SidebarProps {
  activeMenu: string;
  handleMenuClick: (menu: string) => void;
  handleLogout: () => void;
}

const Sidebar: React.FC<SidebarProps> = ({
  activeMenu,
  handleMenuClick,
  handleLogout,
}) => {
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
          <li
            onClick={() => handleMenuClick("new-alarm")}
            className={`px-4 py-2 text-white ${
              activeMenu === "new-alarm" ? "bg-primary-500" : ""
            } hover:bg-primary-500 hover:text-white cursor-pointer`}
          >
            New Alarm
          </li>
          <li
            onClick={() => handleMenuClick("alarms")}
            className={`px-4 py-2 text-white ${
              activeMenu === "alarms" ? "bg-primary-500" : ""
            } hover:bg-primary-500 hover:text-white cursor-pointer`}
          >
            Alarms
          </li>
          <li
            onClick={() => handleMenuClick("fire-departments")}
            className={`px-4 py-2 text-white ${
              activeMenu === "fire-departments" ? "bg-primary-500" : ""
            } hover:bg-primary-500 hover:text-white cursor-pointer`}
          >
            Fire Departments
          </li>
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
      <div className="text-white text-sm text-center font-semibold mb-1">
        Welcome, mdrogosz
      </div>

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
