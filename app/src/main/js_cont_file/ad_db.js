// <!DOCTYPE html>
<html>
<head>
    <title>JavaScript Database</title>
</head>
<body>
    <h1>JavaScript Database Example</h1>
    
    <button onclick="addData()">Add Data</button>
    <button onclick="displayData()">Display Data</button>
    
    <div id="output"></div>
    <form id="image-upload-form" enctype="multipart/form-data">
        <input type="file" id="image-input" accept="image/*" />
        <button type="button" onclick="uploadImage()">Upload Image</button>
    </form>


    <div id="image-preview">
        <img id="uploaded-image" src="" alt="Uploaded Image" style="display: none;" />
    </div>

    <script>
        // Function to handle image upload
        function uploadImage() {
            const imageInput = document.getElementById("image-input");
            const uploadedImage = document.getElementById("uploaded-image");

            if (imageInput.files.length > 0) {
                const file = imageInput.files[0];
                const reader = new FileReader();

                reader.onload = function(e) {
                    uploadedImage.src = e.target.result;
                    uploadedImage.style.display = "block";
                };

                reader.readAsDataURL(file);
            }
        }
    </script>

    <script>
        // Initialize an array to act as an in-memory database.
        const database = [];

        // Function to add data to the database.
        function addData() {
            const data = {
                name: "John Doe",
                age: 30,
                email: "john.doe@example.com"
            };
            database.push(data);
            console.log("Data added to the database.");
        }

        // Function to display data from the database.
        function displayData() {
            const output = document.getElementById("output");
            output.innerHTML = ""; // Clear previous output.

            database.forEach((data, index) => {
                const dataString = `Entry ${index + 1}: Name: ${data.name}, Age: ${data.age}, Email: ${data.email}`;
                const dataDiv = document.createElement("div");
                dataDiv.textContent = dataString;
                output.appendChild(dataDiv);
            });
        }
    </script>
</body>
</html>
