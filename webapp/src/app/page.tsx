"use client";

import { useState } from "react";
import Sidebar from "../components/Sidebar";
import Dashboard from "../components/Dashboard";

export default function Home() {
  const [activeMenu, setActiveMenu] = useState("new-alarm"); // Default active menu

  const handleMenuClick = (menu: string) => {
    setActiveMenu(menu);
    // Add code here to handle menu option click and navigate to the corresponding page
  };

  const handleLogout = () => {
    // Add code here to handle logout
    console.log("Logout clicked");
  };

  return (
    <main>
      <section className="bg-gray-900">
        <div className="flex flex-row h-screen">
          <Sidebar
            activeMenu={activeMenu} // Pass the active menu as a prop
            handleMenuClick={handleMenuClick} // Pass the click handler as a prop
            handleLogout={handleLogout} // Pass the logout handler as a prop
          />
          <Dashboard activeMenu={activeMenu} />{" "}
          {/* Pass the active menu as a prop */}
        </div>
      </section>
    </main>
  );
}
