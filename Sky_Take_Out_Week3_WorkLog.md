
## 🗓️ **Week 3: March 31 - April 4, 2025**

During this week, I made substantial progress in developing and integrating critical features into the Sky Take Out system, particularly focused on dish and set meal management. The work included not only backend service implementations but also front-end testing and business logic enhancements.

### **March 31, 2025 (Monday)**
- Implemented **automatic filling of common fields** (e.g., creation time, update time, user ID) using AOP (Aspect-Oriented Programming).
- Created custom `@AutoFill` annotation and the `AutoFillAspect` to intercept relevant Mapper methods and inject field values.
- Simplified repetitive code across employee and dish category management services.

### **April 1, 2025 (Tuesday)**
- Developed the **"Add New Dish"** functionality:
  - Integrated file upload using Aliyun OSS for dish images.
  - Built dish creation forms that support adding custom flavor profiles.
  - Saved both dish and corresponding flavor data into the database with transactional consistency.

### **April 2, 2025 (Wednesday)**
- Implemented **dish pagination query**:
  - Added filters for name, category, and status.
  - Supported front-end UI to paginate dishes with relevant display fields like image and category name.

### **April 3, 2025 (Thursday)**
- Completed the **dish deletion feature**:
  - Handled both single and batch deletions.
  - Enforced business rules preventing deletion of dishes currently on sale or associated with active set meals.
  - Deleted corresponding flavor data if a dish was successfully removed.

### **April 4, 2025 (Friday)**
- Finished the **update dish functionality**:
  - Enabled pre-filling forms by fetching dish and flavor data via ID.
  - Updated dish and flavor records with full overwrite and re-insertion strategy.
  - Verified that updates are visible in real-time via front-end interfaces.

---

### ✅ Additional Module: Set Meal Management
Throughout the week, I also completed the full development cycle for the **Set Meal Management Module**, which included:
- Adding new set meals with associated dishes.
- Pagination and querying of set meal listings.
- Modifying and deleting existing set meals.
- Implementing on-sale/off-sale toggles with data consistency.

These features were developed with a strong emphasis on:
- **Interface design** based on product requirements.
- **Database relationship clarity** between categories, dishes, set meals, and their associations.
- Rigorous **testing** using Swagger and UI-level integration.

---

By the end of Week 3, the system supports full CRUD for both dishes and set meals with robust business rule enforcement, significantly advancing the Sky Take Out project’s core functionality.
