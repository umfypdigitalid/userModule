# userModule

In this project, we proposed a structure where the digital ID system will be constructed by 3 modules, which are Issuer module, User module and Relaying party module. Issuer module will verify, approve or reject users’ request for digital ID, User module is a mobile application for users to register , manage their digital ID and sign in to thirdparty website through QR scanning while Relying Party module is any third party application who wish to verify users’ identity using digital ID. User module will work with Issuer module and Relying Party module. User module will also involve database and blockchain.

The objective of user module is:
1) To allow user to register their digital ID
2) To allow user manage the access of third-party to their personal data 
3) To allow user sign in third-party website using digital ID through QR code scanning.



**Login and Register Module**

![image](https://user-images.githubusercontent.com/82718271/159155084-101f9e08-e16d-4833-b105-04ac4e456761.png)

Users need to fill in their personal information to sign up an account. After signed up, users are able to log in to their account using email and password.


**Home Page and Personal Data Module**

![image](https://user-images.githubusercontent.com/82718271/159155117-1c0326c8-2f30-402b-a8bf-067159f9b96b.png)

After login, uses will be directed to the home page of the application. Users can view their personal data and also account verification status when clicking on the “PERSONAL DATA” button.


**Contact Us Module**

![image](https://user-images.githubusercontent.com/82718271/159155134-ad692ad5-124e-4729-9281-2e4483369c20.png)

Users are able to get the contact information such as phone number and email of Digital ID platform when they click on the “Contact Us” in the navigation drawer menu. 


**Logout Module**

![image](https://user-images.githubusercontent.com/82718271/159155139-8b20100a-60ab-4c5c-b73e-56ba8f5283bd.png)

User able to logout the application by clicking the log out button in the drawer menu.


**Account Verification Module**

![image](https://user-images.githubusercontent.com/82718271/159155140-15938e3c-7dd2-431c-a43f-47e03668df7d.png)

If the account verification status of the users are not verified, they are not able to use the QR generator and QR scanner feature. Then, users will be asked to verify their account at the counter when they want to generate or scan QR. Users need to scan QR code at the registration counter to verify their account. Once the account is verified, the application will store the contract address and hash claim in the phone.


**QR Geenerator and QR Scanner Module**

![image](https://user-images.githubusercontent.com/82718271/159155144-9d5ff97f-ae4f-4272-abf3-b165c5c21d59.png)

Once the account has been verified by the issuer, users are allowed to use the QR feature. Before generating QR, users are able to choose which information will be shared with third-party. When the generate button is pressed, QR code will be generated with the chosen data and will be refreshed every 30 seconds. The application also allows users to scan QR code from third-party website. An access request will be prompted to allow user either accept or reject the access request of third-party website to their personal data.


**Login History Module**

![image](https://user-images.githubusercontent.com/82718271/159155154-f9b6e928-4b8b-4248-a774-240d201295f6.png)

User can view the Login History when pressed on the “Login History” in the navigation drawer. A list of 10 recently logged in history will be displayed. User can also view on the detail of the history when click on the history in the list.
