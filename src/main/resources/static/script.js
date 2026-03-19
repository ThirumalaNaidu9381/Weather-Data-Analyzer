const API_BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    // DOM Elements
    const addForm = document.getElementById('temperature-form');
    const tempInput = document.getElementById('temperature-input');
    const messageEl = document.getElementById('add-message');
    
    const btnAvg = document.getElementById('btn-avg');
    const valAvg = document.getElementById('avg-temp');
    
    const btnHigh = document.getElementById('btn-high');
    const valHigh = document.getElementById('high-temp');
    
    const btnLow = document.getElementById('btn-low');
    const valLow = document.getElementById('low-temp');
    
    const btnRefresh = document.getElementById('btn-refresh');
    const tableBody = document.getElementById('temperature-list');

    // Weather elements
    const btnWeather = document.getElementById('btn-weather');
    const cityInput = document.getElementById('city-input');
    const weatherResult = document.getElementById('weather-result');
    const weatherError = document.getElementById('weather-error');
    const weatherCity = document.getElementById('weather-city');
    const weatherDesc = document.getElementById('weather-desc');
    const weatherTemp = document.getElementById('weather-temp');
    const weatherWind = document.getElementById('weather-wind');
    const citySuggestions = document.getElementById('city-suggestions');

    // Autocomplete Logic
    if (cityInput) {
        let debounceTimer;
        cityInput.addEventListener('input', (e) => {
            clearTimeout(debounceTimer);
            const val = e.target.value.trim();
            if (val.length < 2) {
                if (citySuggestions) citySuggestions.innerHTML = '';
                return;
            }
            debounceTimer = setTimeout(async () => {
                try {
                    const res = await fetch(`https://geocoding-api.open-meteo.com/v1/search?name=${encodeURIComponent(val)}&count=5&language=en&format=json`);
                    if (res.ok) {
                        const data = await res.json();
                        if (data.results && citySuggestions) {
                            citySuggestions.innerHTML = '';
                            data.results.forEach(city => {
                                const option = document.createElement('option');
                                option.value = city.name;
                                option.textContent = `${city.name} (${city.country || ''})`;
                                citySuggestions.appendChild(option);
                            });
                        }
                    }
                } catch (err) {
                    console.error('Autocomplete error', err);
                }
            }, 300);
        });
    }

    // Chart instances
    let dayChartInstance = null;
    let monthChartInstance = null;
    let isCityMode = false;

    // Initialize data and stats
    async function initializeApp() {
        await fetchAllTemperatures();
        btnAvg.click();
        btnHigh.click();
        btnLow.click();
    }
    
    initializeApp();

    // Event Listeners
    addForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (!isCityMode) {
            showMessage('Please search for a city first before adding remote temperatures!', 'error');
            return;
        }

        const value = parseFloat(tempInput.value);
        if (isNaN(value)) return;

        try {
            const response = await fetch(`${API_BASE_URL}/add-temperature`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ value })
            });

            if (response.ok) {
                showMessage('Temperature added successfully!', 'success');
                tempInput.value = '';

                await fetchAllTemperatures();
                // Update stats automatically
                btnAvg.click();
                btnHigh.click();
                btnLow.click();
            } else {
                showMessage('Failed to add temperature.', 'error');
            }
        } catch (error) {
            console.error('Error:', error);
            showMessage('Error connecting to server.', 'error');
        }
    });

    btnAvg.addEventListener('click', async () => {
        if (isCityMode && dayChartInstance && dayChartInstance.data.datasets[0].data.length > 0) {
            const temps = dayChartInstance.data.datasets[0].data;
            const avg = temps.reduce((a, b) => a + b, 0) / temps.length;
            animateValue(valAvg, avg);
            return;
        }
        try {
            const response = await fetch(`${API_BASE_URL}/average`);
            const data = await response.json();
            const isEmpty = tableBody.querySelectorAll('tr').length === 1 && tableBody.querySelector('.empty-state') !== null;
            if (isEmpty) valAvg.textContent = '-- °C';
            else animateValue(valAvg, data);
        } catch (error) {
            console.error('Error fetching average:', error);
            valAvg.textContent = 'Error';
        }
    });

    btnHigh.addEventListener('click', async () => {
        if (isCityMode && dayChartInstance && dayChartInstance.data.datasets[0].data.length > 0) {
            const temps = dayChartInstance.data.datasets[0].data;
            animateValue(valHigh, Math.max(...temps));
            return;
        }
        try {
            const response = await fetch(`${API_BASE_URL}/highest`);
            const data = await response.json();
            const isEmpty = tableBody.querySelectorAll('tr').length === 1 && tableBody.querySelector('.empty-state') !== null;
            if (isEmpty) valHigh.textContent = '-- °C';
            else animateValue(valHigh, data);
        } catch (error) {
            console.error('Error fetching highest:', error);
            valHigh.textContent = 'Error';
        }
    });

    btnLow.addEventListener('click', async () => {
        if (isCityMode && dayChartInstance && dayChartInstance.data.datasets[0].data.length > 0) {
            const temps = dayChartInstance.data.datasets[0].data;
            animateValue(valLow, Math.min(...temps));
            return;
        }
        try {
            const response = await fetch(`${API_BASE_URL}/lowest`);
            const data = await response.json();
            const isEmpty = tableBody.querySelectorAll('tr').length === 1 && tableBody.querySelector('.empty-state') !== null;
            if (isEmpty) valLow.textContent = '-- °C';
            else animateValue(valLow, data);
        } catch (error) {
            console.error('Error fetching lowest:', error);
            valLow.textContent = 'Error';
        }
    });

    btnRefresh.addEventListener('click', async () => {
        await fetchAllTemperatures();
        btnAvg.click();
        btnHigh.click();
        btnLow.click();
    });

    if (btnWeather) {
        btnWeather.addEventListener('click', async () => {
            const city = cityInput.value.trim();
            if (!city) return;

            weatherError.style.display = 'none';
            weatherResult.style.display = 'none';
            btnWeather.textContent = 'Loading...';

            try {
                const response = await fetch(`${API_BASE_URL}/current-weather?city=${encodeURIComponent(city)}`);
                if (!response.ok) {
                    throw new Error('City not found or API error');
                }
                const data = await response.json();
                
                weatherCity.textContent = city.charAt(0).toUpperCase() + city.slice(1);
                weatherDesc.textContent = data.weatherCondition;
                weatherTemp.textContent = `${data.temperature.toFixed(1)} °C`;
                weatherWind.textContent = `Wind: ${data.windSpeed} km/h`;
                
                weatherResult.style.display = 'block';

                // Delete all current temperatures from backend list
                await fetch(`${API_BASE_URL}/temperatures`, { method: 'DELETE' });

                isCityMode = true;

                // Update the Day and Month graphs with the real-time API data FIRST
                updateChartsWithRealData(data);

                // Refresh the list (this auto-builds on top of baseline chart)
                await fetchAllTemperatures();

                // Now calculate the stats reliably from the updated dataset instances
                btnAvg.click();
                btnHigh.click();
                btnLow.click();

            } catch (error) {
                console.error('Weather error:', error);
                weatherError.textContent = 'Could not fetch weather. Please check the city name.';
                weatherError.style.display = 'block';
            } finally {
                btnWeather.textContent = 'Get Weather';
            }
        });
    }

    // Event delegation for dynamically added delete buttons
    tableBody.addEventListener('click', async (e) => {
        if (e.target.classList.contains('delete-btn')) {
            const id = e.target.getAttribute('data-id');
            if (!id) return;
            try {
                const response = await fetch(`${API_BASE_URL}/temperatures/${id}`, {
                    method: 'DELETE'
                });
                if (response.ok) {
                    showMessage('Temperature deleted successfully!', 'success');
                    await fetchAllTemperatures();
                    // Update stats automatically after deletion
                    btnAvg.click();
                    btnHigh.click();
                    btnLow.click();
                } else {
                    showMessage('Failed to delete temperature.', 'error');
                }
            } catch (error) {
                console.error('Error:', error);
                showMessage('Error connecting to server.', 'error');
            }
        }
    });

    // Helper functions
    async function fetchAllTemperatures() {
        try {
            const response = await fetch(`${API_BASE_URL}/temperatures`);
            if (!response.ok) throw new Error('Network response was not ok');
            const data = await response.json();
            renderTable(data);
        } catch (error) {
            console.error('Error fetching temperatures:', error);
            tableBody.innerHTML = `<tr><td colspan="4" class="empty-state" style="color: #ef4444;">Could not connect to the backend server. Make sure the Spring Boot application is running.</td></tr>`;
        }
    }

    function renderTable(data) {
        tableBody.innerHTML = '';
        
        // Update charts with new data (if city search hasn't overriden it)
        if (!isCityMode) {
            updateCharts(data);
        } else {
            // Rebuild the chart using baseline + active manual data
            if (window.baseDayLabels && window.baseDayValues && dayChartInstance) {
                const combinedDayLabels = [...window.baseDayLabels];
                const combinedDayValues = [...window.baseDayValues];
                if (data && data.length > 0) {
                    data.forEach(item => {
                        const d = new Date(item.timestamp || Date.now());
                        combinedDayLabels.push(`${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')} (Manual)`);
                        combinedDayValues.push(item.value);
                    });
                }
                dayChartInstance.data.labels = combinedDayLabels;
                dayChartInstance.data.datasets[0].data = combinedDayValues;
                dayChartInstance.update();
            }
            if (window.baseMonthLabels && window.baseMonthValues && monthChartInstance) {
                const combinedMonthLabels = [...window.baseMonthLabels];
                const combinedMonthValues = [...window.baseMonthValues];
                if (data && data.length > 0) {
                    data.forEach(item => {
                        const d = new Date(item.timestamp || Date.now());
                        combinedMonthLabels.push(`${d.getMonth() + 1}-${d.getDate()} (Manual)`);
                        combinedMonthValues.push(item.value);
                    });
                }
                monthChartInstance.data.labels = combinedMonthLabels;
                monthChartInstance.data.datasets[0].data = combinedMonthValues;
                monthChartInstance.update();
            }
        }
        
        if (!data || data.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="4" class="empty-state">No temperature data recorded yet. Add some data above!</td></tr>`;
            return;
        }

        data.forEach((item, index) => {
            const row = document.createElement('tr');
            
            // Format timestamp nicely
            let timeStr = 'N/A';
            if (item.timestamp) {
                const date = new Date(item.timestamp);
                timeStr = date.toLocaleString();
            }

            row.innerHTML = `
                <td>${index + 1}</td>
                <td style="font-weight: 500;">${item.value.toFixed(1)} °C</td>
                <td style="color: var(--text-muted); font-size: 0.9em;">${timeStr}</td>
                <td><button class="btn btn-outline btn-sm delete-btn" data-id="${item.id}" style="padding: 0.25rem 0.5rem; font-size: 0.8rem; border-color: #ef4444; color: #ef4444;">Delete</button></td>
            `;
            tableBody.appendChild(row);
        });
    }

    function showMessage(text, type) {
        messageEl.textContent = text;
        messageEl.className = `message ${type}`;
        setTimeout(() => {
            messageEl.textContent = '';
            messageEl.className = 'message';
        }, 3000);
    }

    function animateValue(element, newValue) {
        element.style.transform = 'scale(1.1)';
        element.style.color = 'var(--secondary-color)';
        setTimeout(() => {
            element.textContent = `${newValue.toFixed(1)} °C`;
            element.style.transform = 'scale(1)';
            element.style.color = 'var(--primary-color)';
        }, 150);
    }

    function resetStats() {
        valAvg.textContent = '-- °C';
        valHigh.textContent = '-- °C';
        valLow.textContent = '-- °C';
    }

    function updateChartsWithRealData(weatherData) {
        if (!weatherData.hourlyTimes || !weatherData.dailyTimes) return;

        window.baseDayLabels = [...weatherData.hourlyTimes];
        window.baseDayValues = [...weatherData.hourlyTemps];
        window.baseMonthLabels = [...weatherData.dailyTimes];
        window.baseMonthValues = [...weatherData.dailyTemps];

        const dayLabels = [...window.baseDayLabels];
        const dayValues = [...window.baseDayValues];
        const monthLabels = [...window.baseMonthLabels];
        const monthValues = [...window.baseMonthValues];

        // Update Day Chart
        const ctxDay = document.getElementById('dayChart').getContext('2d');
        if (dayChartInstance) {
            dayChartInstance.data.labels = dayLabels;
            dayChartInstance.data.datasets[0].data = dayValues;
            dayChartInstance.update();
        } else {
            dayChartInstance = new Chart(ctxDay, {
                type: 'line',
                data: {
                    labels: dayLabels,
                    datasets: [{
                        label: 'Temperature (°C)',
                        data: dayValues,
                        borderColor: '#3b82f6',
                        backgroundColor: 'rgba(59, 130, 246, 0.1)',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.3
                    }]
                },
                options: { responsive: true, maintainAspectRatio: false, plugins: { legend: { display: false } } }
            });
        }

        // Update Month Chart
        const ctxMonth = document.getElementById('monthChart').getContext('2d');
        if (monthChartInstance) {
            monthChartInstance.data.labels = monthLabels;
            monthChartInstance.data.datasets[0].data = monthValues;
            monthChartInstance.update();
        } else {
            monthChartInstance = new Chart(ctxMonth, {
                type: 'line',
                data: {
                    labels: monthLabels,
                    datasets: [{
                        label: 'Temperature (°C)',
                        data: monthValues,
                        borderColor: '#10b981',
                        backgroundColor: 'rgba(16, 185, 129, 0.1)',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.3
                    }]
                },
                options: { responsive: true, maintainAspectRatio: false, plugins: { legend: { display: false } } }
            });
        }
    }

    function updateCharts(data) {
        const today = new Date();
        const currentMonth = today.getMonth();
        const currentYear = today.getFullYear();
        const currentDate = today.getDate();

        // 1. Prepare Day Data (Today only)
        // Sort by timestamp for proper line charting
        const sortedData = [...data].sort((a, b) => new Date(a.timestamp) - new Date(b.timestamp));
        
        const dayData = sortedData.filter(item => {
            const date = new Date(item.timestamp);
            return date.getDate() === currentDate && date.getMonth() === currentMonth && date.getFullYear() === currentYear;
        });

        const dayLabels = dayData.map(item => {
            const d = new Date(item.timestamp);
            return `${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}`;
        });
        const dayValues = dayData.map(item => item.value);

        // 2. Prepare Month Data (This Month)
        const monthData = sortedData.filter(item => {
            const date = new Date(item.timestamp);
            return date.getMonth() === currentMonth && date.getFullYear() === currentYear;
        });

        const monthLabels = monthData.map(item => {
            const d = new Date(item.timestamp);
            return `${d.getDate()}/${d.getMonth() + 1}`;
        });
        const monthValues = monthData.map(item => item.value);

        // Update Day Chart
        const ctxDay = document.getElementById('dayChart').getContext('2d');
        if (dayChartInstance) {
            dayChartInstance.data.labels = dayLabels;
            dayChartInstance.data.datasets[0].data = dayValues;
            dayChartInstance.update();
        } else {
            dayChartInstance = new Chart(ctxDay, {
                type: 'line',
                data: {
                    labels: dayLabels,
                    datasets: [{
                        label: 'Temperature (°C)',
                        data: dayValues,
                        borderColor: '#3b82f6',
                        backgroundColor: 'rgba(59, 130, 246, 0.1)',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.3
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: { display: false }
                    },
                    scales: {
                        y: { 
                            beginAtZero: false,
                            suggestedMin: 10,
                            suggestedMax: 40
                        }
                    }
                }
            });
        }

        // Update Month Chart
        const ctxMonth = document.getElementById('monthChart').getContext('2d');
        if (monthChartInstance) {
            monthChartInstance.data.labels = monthLabels;
            monthChartInstance.data.datasets[0].data = monthValues;
            monthChartInstance.update();
        } else {
            monthChartInstance = new Chart(ctxMonth, {
                type: 'line',
                data: {
                    labels: monthLabels,
                    datasets: [{
                        label: 'Temperature (°C)',
                        data: monthValues,
                        borderColor: '#10b981',
                        backgroundColor: 'rgba(16, 185, 129, 0.1)',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.3
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: { display: false }
                    },
                    scales: {
                        y: { 
                            beginAtZero: false,
                            suggestedMin: 10,
                            suggestedMax: 40
                        }
                    }
                }
            });
        }
    }
});
