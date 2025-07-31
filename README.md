# Hệ thống Sát hạch Bằng lái xe

## Tổng quan

Hệ thống quản lý sát hạch bằng lái xe được xây dựng bằng JavaFX với thiết kế Material Design, hỗ trợ 3 vai trò chính:

- **Admin (Quản trị viên)**: Quản lý toàn bộ hệ thống
- **Examiner (Giám thị)**: Coi thi và chấm điểm
- **Candidate (Thí sinh)**: Đăng ký và tham gia thi

## Yêu cầu hệ thống

- **JDK**: Version 23
- **IDE**: Apache NetBeans 23 (hoặc IntelliJ IDEA, Eclipse)
- **Build tool**: Maven
- **JavaFX**: Version 21.0.2

## Cài đặt và chạy

### 1. Clone dự án

```bash
git clone <repository-url>
cd OOPSH
```

### 2. Build dự án

```bash
mvn clean compile
```

### 3. Chạy ứng dụng

```bash
mvn javafx:run
```

### 4. Hoặc chạy từ IDE

- Mở dự án trong NetBeans/IntelliJ
- Chạy class `com.pocitaco.oopsh.App`

## Tài khoản mặc định

### Admin

- **Username**: `admin`
- **Password**: `admin123`
- **Role**: Admin

### Giám thị

- **Username**: `giamthi001`
- **Password**: `gt123456`
- **Role**: Examiner

### Thí sinh

- **Username**: `thisinh001`
- **Password**: `ts123456`
- **Role**: Candidate

## Cấu trúc dự án

```
src/
├── main/
│   ├── java/
│   │   └── com/pocitaco/oopsh/
│   │       ├── controllers/          # Controllers cho các màn hình
│   │       ├── dao/                  # Data Access Objects
│   │       ├── enums/                # Enum classes
│   │       ├── models/               # Model classes
│   │       ├── utils/                # Utility classes
│   │       └── App.java              # Main application class
│   └── resources/
│       └── com/pocitaco/oopsh/
│           ├── *.fxml               # FXML files cho giao diện
│           ├── styles/              # CSS files
│           └── icons/               # Icons và images
└── data/                            # XML data files
    ├── users.xml                    # Dữ liệu người dùng
    ├── examtypes.xml               # Dữ liệu loại bằng lái
    ├── exams.xml                   # Dữ liệu kỳ thi
    ├── examschedules.xml           # Dữ liệu lịch thi
    ├── registrations.xml           # Dữ liệu đăng ký
    └── results.xml                 # Dữ liệu kết quả thi
```

## Tính năng chính

### Admin Dashboard

- 📊 Thống kê tổng quan hệ thống
- 📋 Quản lý kỳ thi
- 👥 Quản lý người dùng
- 📅 Quản lý lịch thi
- 🎯 Quản lý hạng bằng lái
- 📈 Thống kê báo cáo
- ⚙️ Cấu hình hệ thống

### Examiner Dashboard

- 📊 Dashboard cá nhân
- 📅 Lịch coi thi
- 📝 Ca thi được phân công
- 📊 Chấm điểm
- 👥 Danh sách thí sinh
- 📈 Thống kê cá nhân

### Candidate Dashboard

- 📊 Dashboard cá nhân
- 👤 Thông tin cá nhân
- 📝 Đăng ký thi
- 📅 Lịch thi của tôi
- 📊 Kết quả thi
- 🏆 Giấy chứng nhận

## Thiết kế Material Design

Giao diện sử dụng nguyên tắc Material Design với:

- **Sidebar navigation**: Menu điều hướng rõ ràng
- **Cards**: Hiển thị thông tin dạng thẻ
- **Color scheme**:
  - Admin: Blue (#1976D2)
  - Examiner: Green (#4CAF50)
  - Candidate: Orange (#FF9800)
- **Typography**: Font Segoe UI, Roboto
- **Shadows và Effects**: Drop shadows cho depth
- **Responsive**: Giao diện thích ứng

## Cơ sở dữ liệu XML

Hệ thống sử dụng XML files để lưu trữ dữ liệu:

- **users.xml**: Thông tin người dùng (Admin, Giám thị, Thí sinh)
- **examtypes.xml**: Các loại bằng lái (A1, A2, B1, B2, C, D, E, F)
- **exams.xml**: Thông tin các kỳ thi
- **examschedules.xml**: Lịch thi chi tiết
- **registrations.xml**: Đăng ký thi của thí sinh
- **results.xml**: Kết quả thi

## Hướng dẫn phát triển

### Thêm chức năng mới

1. Tạo FXML file trong `resources/com/pocitaco/oopsh/`
2. Tạo Controller class trong `controllers/`
3. Thêm navigation trong dashboard tương ứng
4. Cập nhật DAO nếu cần thao tác dữ liệu

### Thêm model mới

1. Tạo model class trong `models/`
2. Tạo DAO class trong `dao/`
3. Thêm enum nếu cần trong `enums/`
4. Cập nhật XML structure

## Troubleshooting

### Lỗi JavaFX module

```bash
# Thêm JVM arguments
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

### Lỗi build Maven

```bash
# Clean và rebuild
mvn clean install
```

### Lỗi XML parsing

- Kiểm tra cấu trúc XML files trong folder `data/`
- Đảm bảo encoding UTF-8

## Roadmap

### Phase 1 (Hiện tại)

- ✅ Cấu trúc dự án cơ bản
- ✅ Authentication system
- ✅ Dashboard cho 3 roles
- ✅ Material Design UI

### Phase 2 (Tiếp theo)

- 🔄 CRUD operations hoàn chỉnh
- 🔄 Exam management
- 🔄 Registration system
- 🔄 Results management

### Phase 3 (Tương lai)

- ⏳ Search và filtering
- ⏳ Statistics và reports
- ⏳ File export (PDF, Excel)
- ⏳ Email notifications

## Liên hệ và hỗ trợ

- **Developer**: Google AI Assistant
- **Project**: OOP-SH (Object-Oriented Programming - Sát Hạch)
- **Version**: 1.0-SNAPSHOT

## License

Dự án được phát triển cho mục đích học tập và nghiên cứu.
