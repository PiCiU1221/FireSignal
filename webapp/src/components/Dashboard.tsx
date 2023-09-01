import React from "react";
import NewAlarmContent from "./NewAlarmContent";
import AlarmsContent from "./AlarmsContent";
import FireDepartmentsContent from "./FireDepartmentsContent";
import FireEnginesContent from "./FireEnginesContent";
import FirefightersContent from "./FirefightersContent";

interface DashboardProps {
  activeMenu: string;
}

const Dashboard: React.FC<DashboardProps> = ({ activeMenu }) => {
  return (
    <div className="flex flex-col items-center justify-center flex-1 px-6 py-8 mx-auto">
      {activeMenu === "new-alarm" && <NewAlarmContent />}
      {activeMenu === "alarms" && <AlarmsContent />}
      {activeMenu === "fire-departments" && <FireDepartmentsContent />}
      {activeMenu === "fire-engines" && <FireEnginesContent />}
      {activeMenu === "firefighters" && <FirefightersContent />}
    </div>
  );
};

export default Dashboard;
