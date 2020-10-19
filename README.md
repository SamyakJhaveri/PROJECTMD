# Project, M.D.
A speedy Android app system that efficiently delivers diagnostic information written by pathologists to their fellow doctors.
## Table of Contents
- [Introduction](#Introduction)
  - Problem
  - Aim
  - Approach
- [Technologies Used](#Technologies-Used)
- [Features](#Features)
- [Demo and Presentation](#Demo-and-Presentation)
- [Future Scope](#Future-Scope)
- [Societal Implementation](#Societal-Implementation)

## Introduction
### Problem
Pathology is a field that has seen significant advancements in technology, from better techniques to non-invasively collect tissue samples and blood samples to revolutionizing 
ways to detect diseases with artificial intelligence. But all these new technologies come at enormous costs, which most hospitals in the world are unable to adopt, much less a 
developing country like India with a sparse patient-to-doctor ratio. Almost 122 Indians per 100,000 die due to the low quality of healthcare each year.<br>

### Aim
Medical information is sensitive and private. Typical diagnosis involves the transfer of medical documents from one department to another (microbiology to histopathology, 
for example), resulting in misplacement of sensitive data.<br>
What our doctors in government hospitals need is a speedy system that is convenient, sensible, simple, and secure; so that they can deliver the results and diagnosis to 
the patients at the right time with the required accuracy.One of the main driving forces of digital pathology is the speed of delivery.<br> 
We have attempted to build an Android App to digitize the process of a pathologist’s diagnosis of diseases so that the sensitive data is conveniently shareable. 
It also makes microscopy easier and reduces the occupational hazard to the pathologists’ necks associated with long hours of peering through the microscope.

### Approach
A 3D-printed Universal smartphone holder was custom-designed to align and firmly position any smartphone’s camera with a regular microscope’s eyepiece lens to take 
pictures of the slides under observation. Maneuvering the slide images and transfer of data became easy through custom fillable PDFs which were designed in close
collaboration with my mother and her colleagues(Pathologists at VS Hospital), a government hospital in Ahmedabad, India.

## Technologies Used
1. Google's Firebase for Authentication <br>
Using Firebase Authentication makes building secure authentication system easier and also improved the sign-in and on-boarding experience for the less tech savvy users. 
We have used Google authentication which enables user to login with their google account.

2. Glide API for Images <br>
Glide is an open source image loading and caching library for Android apps. Using this, we were able to load high-resolution images of different slides in our app.

3. Android SQLite for Local Data storage <br>
We have used this to store patient’s data on the phone locally. The main reason behind using SQLite and not using Firebase is that medical data needs to be secure. 
By making it local the data is restricted only to the device in which it is stored.

4. 3D Printing <br>
Specially designed 3D-Printed Universal Smartphone Adapter for using all smartphones with a microscope

## Features

- Lab Test or Investigation-specific fillable PDF forms that have been created in collaboration with actual pathologists (who are the target audience for this app)
- Designed to be intuitive and pre-cognitive, using Google’s Material Design Guidelines, keeping in mind the less tech-savvy nature of its target audience
- All the images and filled forms that contain sensitive medical data are stored locally in the phone’s memory using SQLite Database for best security
- The PDF form can be edited to have e-Signatures, approval stamps, annotations, full-resolution image embedding, and a whole suite of more features that we added to the app using the 
PDFTron SDK as a PDF Viewer base.
- All the images and filled forms can be sent to a peer doctor in the form of a locked and password-protected PDF using apps like Gmail and WhatsApp.

## Demo and Presentation
Link to Demo Walkthrough video: (insert Walkthrough video from Google Drive here) <br>
Link to Presentation for IETE Software Competition, 2019 (First Prize from amongst 130+ teams, got 2 internship offers one from Reliance): https://docs.google.com/presentation/d/1AoORRi6xBqqANJxLTnVOVHSb1fngFirPw1g3Bg9bS6k/edit?usp=sharing


## Future Scope
After consulting pathologists and testing the app on real microscopes in a government hospital for the second time, we received positive responses, giving us a deep 
insight into our user needs. <br> 
Link to Interview with Pathologists and Resident Doctors at NHL Medical College: (Insert interview video link from Google Drive here) <br> <br>

We have even approached our Computer and Network Prof. Ratnesh Chaturvedi for a joint research paper for adding extra security to our app for more secure data transfer.
One of the doctors suggested adding the doctor’s authentication number and their essential identification, ensuring security while sharing sensitive medical data.
We plan to take up a machine learning course Google’s TensorFlow, to analyze those images with convolutional neural networks and deep neural networks to detect 
biomarkers in the tissue slides for disease detection.<br> <br>
After three months of testing and redesign, we were able to build a user friendly system for the pathologists at VS Hospital. The Android app made their day-to-day tasks 
of tracking diagnostic forms, taking microscopic images, transferring sensitive medical information to their peers for a second-opinion a lot faster with their smartphones’ 
convenience. 

## Societal Implementation
Our project primarily caters to the needs of Pathologists in government hospitals and Medical Resident students. It provides an effective and efficient way to deal with 
Medical information such as images and PDFs of the specimen slides under the microscope that involves hypothesis, diagnosis and conclusion as observed by the pathologists. <br>
Throughout the development processes the design has revolved around boosting the speed-of-delivery of each patient’s data from one pathologist to another in conveniently 
shareable formats.