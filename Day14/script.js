const apiURL = "https://dummy.restapiexample.com/api/v1/employees";

async function fetchEmployeeData() {
  try {
    const response = await fetch(apiURL);

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();

    console.log("Employee Data:", data);

  } catch (error) {
    console.error("Error fetching data:", error);
  }
}

// Call the async function
fetchEmployeeData();
