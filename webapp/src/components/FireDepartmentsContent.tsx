import React, { useState, useEffect } from "react";
import axios from "axios";

interface FireDepartment {
  id: number;
  departmentName: string;
  departmentCity: string;
  departmentStreet: string;
}

const FireDepartmentsContent: React.FC = () => {
  const [fireDepartments, setFireDepartments] = useState<FireDepartment[]>([]);
  const [page, setPage] = useState<number>(0);
  const [selectedDepartment, setSelectedDepartment] =
    useState<FireDepartment | null>(null);

  const pageSize = 10; // Number of fire departments per page
  const totalFireDepartments = fireDepartments.length;
  const totalPages = Math.ceil(totalFireDepartments / pageSize);

  useEffect(() => {
    const apiBaseUrl = process.env.NEXT_PUBLIC_API_BASE_URL;

    // Fetch fire departments from your backend API
    const fetchFireDepartments = async () => {
      try {
        const response = await axios.get<FireDepartment[]>(
          `${apiBaseUrl}/api/fire-departments?page=${page}`
        );
        setFireDepartments(response.data);
      } catch (error) {
        console.error("Error fetching fire departments:", error);
      }
    };

    fetchFireDepartments();
  }, [page]);

  const handleDepartmentClick = (department: FireDepartment) => {
    setSelectedDepartment(
      department === selectedDepartment ? null : department
    );
  };

  const handlePreviousPage = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  };

  const handleNextPage = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
    }
  };

  return (
    <div className="flex flex-col h-screen w-full">
      <h2 className="text-3xl mb-4 ml-6">Fire Departments</h2>
      <div className="flex justify-center flex-1 mt-16">
        <div className="w-full">
          <ul>
            {fireDepartments.map((department) => (
              <li key={department.id} className="mb-2">
                <div className="w-full">
                  <button
                    className={`w-full bg-gray-800 text-white p-2 rounded-lg border border-gray-300 ${
                      selectedDepartment === department ? "mb-4" : "mb-2"
                    }`}
                    onClick={() => handleDepartmentClick(department)}
                    style={
                      selectedDepartment === department
                        ? { height: "auto", marginBottom: "1rem" }
                        : {}
                    }
                  >
                    {department.departmentName}
                    <span
                      className={`float-right ${
                        selectedDepartment === department
                          ? "transform rotate-180"
                          : ""
                      } transition-transform duration-300`}
                    >
                      ↓
                    </span>
                    {selectedDepartment === department && (
                      <div className="mt-6">
                        <p>City: {department.departmentCity}</p>
                        <p>Street: {department.departmentStreet}</p>
                        {/* Additional information can be added here */}
                      </div>
                    )}
                  </button>
                </div>
              </li>
            ))}
          </ul>
          <button
            className={`mt-4 bg-green-500 text-white p-2 rounded-lg hover:bg-green-600 ${
              page === 0 ? "opacity-50 cursor-not-allowed" : "bg-gray-300"
            }`}
            onClick={handlePreviousPage}
            disabled={page === 0}
          >
            Back
          </button>
          <button
            className={`mt-4 bg-green-500 text-white p-2 rounded-lg hover:bg-green-600 ${
              page >= totalPages - 1
                ? "opacity-50 cursor-not-allowed"
                : "bg-gray-300"
            }`}
            onClick={handleNextPage}
            disabled={page >= totalPages - 1}
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
};

export default FireDepartmentsContent;
