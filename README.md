📦 Distributed Mining System with Load Balancing
Description:
This project implements a distributed mining system designed to simulate block mining with load balancing across multiple servers. The system uses a client-server architecture with UDP protocol and includes a Load Balancer that distributes mining tasks efficiently. The application ensures data integrity by using hashing techniques that require discovering a nonce value that meets specific criteria (e.g., generating a hash with a defined number of leading zeros).

🚀 Project Components
🖥️ Client — Sends mining requests to the Load Balancer.
⚖️ Load Balancer — Dynamically assigns mining ranges to available servers.
🔎 Servers — Mine the required hash values in parallel to boost performance.
📊 Execution Time Analysis — Evaluates system scalability and the effectiveness of load balancing.
🔑 Key Features
✅ Efficient load balancing for mining tasks.
✅ Parallel processing to accelerate the nonce discovery process.
✅ Comprehensive performance analysis based on mining difficulty and number of clients.
✅ Clear code structure with detailed comments for improved readability.

📚 About the Course
This project was developed as part of the CSC408 - Distributed Information Systems course at Abu Dhabi University.

