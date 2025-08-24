# programme to polt speedup graph 
# radhiya isaacs with assistance from chatGPT

import pandas as pd
import matplotlib.pyplot as plt

# Load benchmark results
df = pd.read_csv("benchmark_results.csv")

# Plot Serial vs Parallel time
plt.figure(figsize=(10, 6))
plt.plot(df['GridSize'], df['SerialTime'], marker='o', label='Serial')
plt.plot(df['GridSize'], df['ParallelTime'], marker='s', label='Parallel')
plt.title("Nightmare: Serial vs Parallel Execution Time")
plt.xlabel("Grid Size")
plt.ylabel("Time (ms)")
plt.legend()
plt.grid(True)
plt.show()

# Plot Speedup = Serial / Parallel
df['Speedup'] = df['SerialTime'] / df['ParallelTime']

plt.figure(figsize=(10, 6))
plt.plot(df['GridSize'], df['Speedup'], marker='^', color='green')
plt.title("Nightmare: Parallel Speedup")
plt.xlabel("Grid Size")
plt.ylabel("Speedup (Serial / Parallel)")
plt.grid(True)
plt.show()
