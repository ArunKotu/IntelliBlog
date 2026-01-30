# IntelliBlog – AI-Enhanced Blogging System

An AI-powered blogging platform with secure authentication and intelligent content summarization using Gemini AI.
Live Demo: https://intelliblog-6.onrender.com/
## 🚀 Features
- User authentication with Spring Security (Basic Auth)
- Role-based access control
- Secure CRUD operations for blogs
- AI-generated summaries for long blog posts
- User-specific blog visibility
- Stateless backend architecture

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot
- **Database:** MongoDB
- **Security:** Spring Security
- **AI Integration:** Gemini API
- **DevOps:** Docker
- **Tools:** Maven, Postman, Git

## 🔐 Security
- Basic Authentication
- Role-based authorization
- Stateless session management
- Secure API access

## 🤖 AI Functionality
- Automatically summarizes long blog posts
- Handles API failures with retry logic
- Improves content readability and engagement

## 🧱 Architecture
- REST-based backend
- Clean separation of concerns
- Production-ready configuration (CORS, env variables)

## 📦 Deployment
- Multi-stage Docker build
- Deployed on Render
- Environment-based configuration

## ▶️ How to Run Locally
```bash
git clone https://github.com/ArunKotu/IntelliBlog
cd intelliblog
mvn clean install
docker build -t intelliblog .
docker run -p 8080:8080 intelliblog
