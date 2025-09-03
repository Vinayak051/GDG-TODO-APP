const themeBtn = document.getElementById('theme-btn');
const body = document.body;
const frm = document.getElementById('add-frm');
const lst = document.getElementById('task-lst');
const API_BASE = 'http://localhost:8080';
let editTaskId = null;

document.addEventListener('DOMContentLoaded', () => {
    setupTheme();
    getTasks();
});

const setupTheme = () => {
    const isDark = localStorage.getItem('dark') === 'true';
    body.classList.toggle('dark', isDark);
    themeBtn.querySelector('.material-symbols-outlined').textContent = isDark ? 'dark_mode' : 'light_mode';
};

const toggleTheme = () => {
    body.classList.toggle('dark');
    localStorage.setItem('dark', body.classList.contains('dark'));
    themeBtn.querySelector('.material-symbols-outlined').textContent = body.classList.contains('dark') ? 'dark_mode' : 'light_mode';
};

const render = (task) => {
    const item = document.createElement('li');
    item.className = 'task';
    item.dataset.id = task.id;
    item.dataset.obj = JSON.stringify(task);
    if (task.status === 'COMPLETED') item.classList.add('done');
    item.innerHTML = `
        <div class="info">
            <span class="title">${task.title}</span>
            <span class="desc">${task.desc || ''}</span>
        </div>
        <div class="acts">
            <button class="i-btn stat-btn">
                <span class="material-symbols-outlined">${task.status === 'COMPLETED' ? 'check_box' : 'check_box_outline_blank'}</span>
            </button>
            <button class="i-btn edit-btn">
                <span class="material-symbols-outlined">edit</span>
            </button>
            <button class="i-btn del-btn">
                <span class="material-symbols-outlined">delete</span>
            </button>
        </div>
    `;
    lst.appendChild(item);
};

const getTasks = async (endpoint = '/tasks') => {
    try {
        const res = await fetch(API_BASE + endpoint);
        const data = await res.json();
        lst.innerHTML = '';
        data.forEach(render);
    } catch (err) {}
};

const addTask = async (e) => {
    e.preventDefault();
    const title = document.getElementById('title-inp').value.trim();
    const desc = document.getElementById('desc-inp').value.trim();
    const dueD = document.getElementById('due-inp').value;

    if (!title) return alert("Title is required");

    const newTask = { title, desc, dueD: dueD || null };

    try {
        const res = await fetch(API_BASE + '/tasks', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newTask)
        });

        if (!res.ok) {
            const errText = await res.text();
            throw new Error(errText || "Failed to add task");
        }

        frm.reset();
        getTasks(); // refresh list
    } catch (err) {
        console.error("Can't add task:", err);
        alert("Error adding task: " + err.message);
    }
};


const handleList = async (e) => {
    const btn = e.target.closest('.i-btn');
    if (!btn) return;
    const item = e.target.closest('.task');
    const id = item.dataset.id;

    if (btn.classList.contains('del-btn')) {
        await fetch(`${API_BASE}/tasks/${id}`, { method: 'DELETE' });
        item.remove();
    }
    if (btn.classList.contains('stat-btn')) {
        const taskData = JSON.parse(item.dataset.obj);
        taskData.status = taskData.status === 'COMPLETED' ? 'PENDING' : 'COMPLETED';
        await fetch(`${API_BASE}/tasks/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(taskData)
        });
        const activeFilter = document.querySelector('.f-option.active');
        getTasks(activeFilter?.dataset.url);
    }
    if (btn.classList.contains('edit-btn')) {
        const taskData = JSON.parse(item.dataset.obj);
        document.getElementById('title-inp').value = taskData.title;
        document.getElementById('desc-inp').value = taskData.desc || '';
        document.getElementById('due-inp').value = taskData.dueD || '';
        editTaskId = id;
    }
};

const filterBtn = document.getElementById('filter-btn');
const filterMenu = document.getElementById('filter-menu');
filterBtn.addEventListener('click', () => filterMenu.style.display = filterMenu.style.display === 'block' ? 'none' : 'block');
document.addEventListener('click', (e) => { if (!filterBtn.contains(e.target) && !filterMenu.contains(e.target)) filterMenu.style.display = 'none'; });
filterMenu.addEventListener('click', (e) => {
    const option = e.target.closest('.f-option');
    if (!option) return;
    document.querySelectorAll('.f-option').forEach(o => o.classList.remove('active'));
    option.classList.add('active');
    getTasks(option.dataset.url);
    filterMenu.style.display = 'none';
});

themeBtn.addEventListener('click', toggleTheme);
frm.addEventListener('submit', addTask);
lst.addEventListener('click', handleList);
