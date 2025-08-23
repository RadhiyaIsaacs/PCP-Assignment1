import pandas as pd
import matplotlib.pyplot as plt

# Load CSV
csv_file = "thread_test_results.csv"
df = pd.read_csv(csv_file)

# Get unique grid sizes
grid_sizes = df['GridSize'].unique()

plt.figure(figsize=(10,6))

# Plot a line for each grid size
for grid in grid_sizes:
    subset = df[df['GridSize'] == grid]
    plt.plot(subset['Threads'], subset['AverageTime(ms)'], marker='o', linestyle='-', label=f'Grid {grid}')

# Labels and title
plt.xlabel("Number of Threads")
plt.ylabel("Average Time (ms)")
plt.title("DungeonHunterThread Performance vs Thread Count")
plt.xticks(df['Threads'].unique())
plt.legend(title="Grid Size")
plt.grid(True)

plt.tight_layout()
plt.show()
