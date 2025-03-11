Description:
This project implements a distributed mining system designed to simulate block mining with load balancing across multiple servers. The system uses a client-server architecture using UDP protocol and includes a Load Balancer that distributes mining tasks efficiently. The application ensures data integrity by using hashing techniques that require discovering a nonce value that meets specific criteria (e.g., generating a hash with a defined number of leading zeros).

The project includes:
A Client that sends mining requests to the Load Balancer.
A Load Balancer that dynamically assigns mining ranges to available servers.
Multiple Servers that mine the required hash values in parallel, enhancing performance.
Comprehensive execution time analysis to evaluate system scalability and the effectiveness of load balancing.

Key features: 
✅ Efficient load balancing for mining tasks.
✅ Parallel processing to accelerate the nonce discovery process.
✅ Comprehensive performance analysis based on mining difficulty and number of clients.
✅ Clear code structure with detailed comments for improved readability.

This project was developed as part of the CSC408 - Distributed Information Systems in Abu Dhabi University
